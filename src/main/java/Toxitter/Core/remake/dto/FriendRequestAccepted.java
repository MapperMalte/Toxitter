package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.remake.dto.output.Transferrable;
import com.google.gson.Gson;

@PushTo(route = "/notifyFriendRequestAccepted", method = "notifyFriendRequestAccepted")
public class FriendRequestAccepted implements Transferrable
{
    public String from;

    @Override
    public String asJSON() {
        return new Gson().toJson(this).toString();
    }

    @Override
    public String asJavaScript()
    {
        return null;
    }

    @Override
    public String asJavaCode() {
        return null;
    }
}
