package examples.toxitter;

import Toxitter.Model.OneToOne;
import Toxitter.Model.Reservoir;
import theory.DiamondList;

import java.util.Date;
import java.util.TreeMap;

public class PostReservoir extends Reservoir
{
    private static TreeMap<String,Post> postsById = new TreeMap<>();
    private static TreeMap<String, DiamondList<Post>> postsByUser = new TreeMap<String, DiamondList<Post>>();

    private static OneToOne<String,Post> as;
    public static Post addPost(String userId, String title, String content)
    {
        Post p = new Post();
        p.id = makeId();
        p.content = content;
        p.title = title;
        p.time = new Date();
        p.ownerId = userId;
        postsById.put(p.id,p);
        if ( postsByUser.containsKey(userId) )
        {
            postsByUser.get(userId).addOnTop(p);
        } else {
            DiamondList<Post> postsByUserList = new DiamondList<>();
            postsByUserList.addOnTop(p);
            postsByUser.put(userId,postsByUserList);
        }
        Feed.addPost(p);
        return p;
    }

    public static Post getPostByPostId(String postId)
    {
        return postsById.get(postId);
    }

    public static DiamondList<Post> getAllPostsByUserId(String userId)
    {
        return postsByUser.get(userId);
    }
}