package ToxitterModel;

import apple.laf.JRSUIUtils;
import com.google.gson.Gson;
import com.locality.toxitterbackend2.Ullog;
import com.locality.toxitterbackend2.Umlauter;
import org.apache.commons.text.StringEscapeUtils;
import theory.DiamondList;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

@FetchAt(route="post")
public class Post
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

    private transient TreeMap<String,Integer> reactingUsers = new TreeMap<>();
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
    public static String reactX(@RequestParam(name = "postId",obligatory = true) String postId,@RequestParam(name = "userId", obligatory = true) String userId, @RequestParam(name = "smileyName", obligatory = true) String smileyName)
    {
        Post p = PostReservoir.getPostByPostId(postId);
        System.out.println("Reacting to Post: "+p.title+" with ID "+postId+" for user "+userId);
        p.react(userId,smileyName);
        return "User "+StringEscapeUtils.escapeHtml4(UserReservoir.getUserByUserId(userId).name)+" liked the post!";
    }

    public void react(String userId, String smileyName)
    {
        /*
        if ( reactingUsers.containsKey(userId) )
        {
            Ullog.put(Post.class,"User "+UserReservoir.getUserByUserId(userId).name+" has already reacted to post with ID "+this.id);
            return;
        }*/
        int emotionId = Emotions.getEmotionId(smileyName);
        if ( emotionId != -1 )
        {
            if ( reactionsByName.containsKey(smileyName) )
            {
                reactionsByName.get(smileyName).count++;
            } else {
                Reaction reaction = new Reaction();
                reaction.smiley = smileyName;
                reaction.count = 1;
                reaction.links.addOnTop(new ProfileLink(userId,"",""));
                reactionsByName.put(smileyName,reaction);
                reactions.addOnTop(reaction);
            }
            reactingUsers.put(userId,emotionId);
            reactionCounter++;
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