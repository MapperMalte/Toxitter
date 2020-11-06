package Avatar.Boxfresh.reservoirs;

import Avatar.Boxfresh.input.CreatePostRequest;
import Avatar.Boxfresh.output.PostCreated;
import Avatar.Boxfresh.routes.Post;
import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.Relation;
import Avatar.Elemental.earth.Reservoir;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.fire.AI.stimulanziae.Investin;

public class PostReservoir
{
    private static Relation<User, Post> userPostRelation = new Relation<>();
    private static Reservoir<Post> allPosts = new Reservoir<>();

    public static ReservoirEntityList<Post> getAllPostsByUser(User user)
    {
        return userPostRelation.forwardGet(user);
    }

    public static Post getBostByPostId(ID key)
    {
        return allPosts.get(key);
    }

    @Investin
    public static PostCreated createPost(CreatePostRequest input)
    {
        Post newPost = new Post(input);
        PostCreated postCreated = new PostCreated();
        postCreated.msg = "Erfolgreich!";
        return postCreated;
    }
}
