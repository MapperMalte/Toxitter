package Avatar.Boxfresh.ui;

import Avatar.Boxfresh.input.CreatePostRequest;
import Avatar.Boxfresh.routes.Chat;
import Avatar.Boxfresh.routes.Post;
import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.water.html.annotations.Card;
import Avatar.Elemental.water.html.annotations.Form;
import Avatar.Elemental.water.html.annotations.Thumbnail;
import Avatar.Elemental.water.html.annotations.UIGroup;

@Avatar.Elemental.water.html.annotations.Toolbar
public class Root extends UIElement
{
    private @UIGroup(index = 0) @Card User profile;
    private @UIGroup(index = 1) @Form CreatePostRequest createPostForm;
    private @UIGroup(index = 1) @Card ReservoirEntityList<Post> feed;
    private @UIGroup(index = 2) @Thumbnail ReservoirEntityList<User> friends;
    private @UIGroup(index = 2) Chat chat;

    @Override
    public UIOutput display()
    {
        return null;
    }
}