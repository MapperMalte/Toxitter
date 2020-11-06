package Avatar.Boxfresh.routes;

import Avatar.Annotations.core.FetchAt;
import Avatar.Boxfresh.output.LikeSignature;
import Avatar.Elemental.earth.Persist;
import Avatar.Annotations.route.Injected;
import Avatar.Boxfresh.input.CreatePostRequest;
import Avatar.Boxfresh.output.PostCreated;
import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.fire.AI.stimulanziae.Investin;
import Avatar.Elemental.water.html.annotations.*;
import Avatar.Elemental.water.html.annotations.styling.Leftmost;

@Persist(primaryKey = "postId", tableName = "user")
@FetchAt(route = "post")
public class Post extends ReservoirEntity
{
    private String postId;
    private String ownerId;
    private @Injected(byId = "ownerId") User user;
    private @Header long time;
    private @Header String title;
    private @Content String content;
    private @Footer @Injected(byId = "postId") LikeSignature likeSignature;

    public Post(CreatePostRequest input)
    {
        setId(new ID());
        content = input.content;
        ownerId = input.ownerId;
        time = System.currentTimeMillis();
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
