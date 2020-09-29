package Toxitter.Boxfresh.output;

import Toxitter.Annotations.core.PushTo;
import Toxitter.Core.OUTPUT;
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
