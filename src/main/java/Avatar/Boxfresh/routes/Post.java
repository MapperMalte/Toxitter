package Avatar.Boxfresh.routes;

import Avatar.Annotations.core.FetchAt;
import Avatar.Elemental.earth.Persist;
import Avatar.Annotations.route.Injected;
import Avatar.Boxfresh.input.CreatePostRequest;
import Avatar.Boxfresh.output.PostCreated;
import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.fire.AI.stimulanziae.Investin;

@Persist(primaryKey = "postId", tableName = "user")
@FetchAt(route = "post")
public class Post extends ReservoirEntity
{
    private static ReservoirEntityList<Post> allPosts;

    private String postId;
    private String ownerId;
    private @Injected User user;
    private String title;
    private String content;
    private long time;

    @Investin
    public static PostCreated createPost(CreatePostRequest input)
    {
        Post newPost = new Post();
        newPost.setId(new ID());
        newPost.content = input.content;
        newPost.ownerId = input.ownerId;
        newPost.time = System.currentTimeMillis();
        allPosts.addOnTop(newPost);
        PostCreated postCreated = new PostCreated();
        postCreated.msg = "Erfolgreich!";
        return postCreated;
    }
    public Post() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
