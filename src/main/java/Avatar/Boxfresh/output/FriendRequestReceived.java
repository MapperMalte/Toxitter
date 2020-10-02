package Avatar.Boxfresh.output;

import Avatar.Annotations.core.PushTo;
import Avatar.Elemental.water.OUTPUT;
import com.google.gson.Gson;

@PushTo(route = "/receiveFriendRequest", method = "notifyFriendRequestReceived")
public class FriendRequestReceived extends OUTPUT
{
    public String fromUserName;
    public String fromUserId;

    @Override
    public String asJSON()
    {
        return new Gson().toJson(this).toString();
    }
}