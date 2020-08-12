package examples.toxitter;

import Toxitter.Model.factoryfresh.User;
import Toxitter.Model.factoryfresh.UserReservoir;
import Toxitter.Core.annotations.FetchAt;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.annotations.Table;
import com.google.gson.Gson;
import Toxitter.Infusion.Umlauter;
import theory.DiamondList;

import java.util.Date;
import java.util.TreeMap;

@FetchAt(route="post")
@Table(primaryKey = "id", tableName = "post")
public class Post extends ReservoirEntity
{
    public String content;
    public String title;
    public String href;
    String ownerId;
    String ownerName;
    String photoUrl;
    String id;
    String referencedPostId = null;

    Date time;
    DiamondList<Reaction> reactions = new DiamondList<>();
    int reactionCounter = 0;

    private transient TreeMap<String,String> reactingUsers = new TreeMap<>();
    private transient TreeMap<String,Reaction> reactionsByName = new TreeMap<>();

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
    public Post()
    {
    }

    public boolean isComment()
    {
        return referencedPostId != null;
    }

    public static class Reaction
    {
        String smiley;
        String html;
        int count = 0;
        transient DiamondList<ProfileLink> links = new DiamondList<>();
    }

    public static class ProfileLink
    {
        String userId;
        String href;
        String photoUrl;
        public ProfileLink(String userId, String href, String photoUrl)
        {
            this.userId = userId;
            this.href = href;
            this.photoUrl = photoUrl;
        }
    }

    @Route(route = "react")
    public static String reactX(
            @RequestParam(name = "postId",obligatory = true) String postId,
            @RequestParam(name = "userId", obligatory = true) String userId,
            @RequestParam(name = "smiley", obligatory = true) String smileyName)
    {
        Post p = PostReservoir.getPostByPostId(postId);
        System.out.println("Reacting to Post: "+p.title+" with ID "+postId+" for user "+userId);
        p.react(userId,smileyName);
        return ""+p.getEmotionCount(smileyName);
    }

    public int getEmotionCount(String smileyName)
    {
        if ( Emotions.isValidEmotion(smileyName) )
        {
            if ( reactionsByName.containsKey(smileyName) )
            {
                return  reactionsByName.get(smileyName).count;
            } else {
                return 0;
            }
        } else {
            throw new IllegalArgumentException("getEmotionCount: "+smileyName+" is not a known smiley");
        }
    }
    public void react(String userId, String smileyName)
    {
        /*
        if ( reactingUsers.containsKey(userId) )
        {
            Ullog.put(Post.class,"User "+UserReservoir.getUserByUserId(userId).name+" has already reacted to post with ID "+this.id);
            return;
        }*/
        if ( Emotions.isValidEmotion(smileyName) )
        {
            if ( reactionsByName.containsKey(smileyName) )
            {
                reactionsByName.get(smileyName).count++;
            } else {
                Reaction reaction = new Reaction();
                reaction.smiley = smileyName;
                reaction.html = Emotions.getHtmlBySmiley(smileyName);
                reaction.count = 1;
                reaction.links.addOnTop(new ProfileLink(userId,"",""));
                reactionsByName.put(smileyName,reaction);
                reactions.addOnTop(reaction);
            }
            reactingUsers.put(userId,smileyName);
            reactionCounter++;
        } else {
            throw new IllegalArgumentException("getEmotionCount: "+smileyName+" is not a known smiley");
        }
        UserNotificationCollector.byUserId(this.ownerId).registerReactionToPost(this, userId, smileyName);
    }

    @Route(route = "create")
    public static Post create(
            @RequestParam(name = "userId", obligatory = true) String userId,
            @RequestParam(name = "title", obligatory = true) String title,
            @RequestParam(name = "content", obligatory = true) String content)
    {
        System.out.println("CREATING POST WITH USERID: "+userId+" Content: "+content+" and title "+title);
        Post p =  PostReservoir.addPost(userId, title, content);
        p.reactions = new DiamondList<>();

        User owner = UserReservoir.getUserByUserId(userId);
        if ( !(owner == null))
        {
            System.out.println("Owner: "+ owner.getName());
            p.ownerName = Umlauter.umlaut(owner.getName());
            p.photoUrl = owner.photoUrl;
        }
        return p;
    }
}