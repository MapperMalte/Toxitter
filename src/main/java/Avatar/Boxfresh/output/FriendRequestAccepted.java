package Avatar.Boxfresh.output;

import Avatar.Annotations.core.PushTo;
import Avatar.Elemental.water.OUTPUT;
import com.google.gson.Gson;

@PushTo(route = "/notifyFriendRequestAccepted", method = "notifyFriendRequestAccepted")
public class FriendRequestAccepted extends OUTPUT
{
    public String from;

    @Override
    public String asJSON() {
        return new Gson().toJson(this).toString();
    }
}
