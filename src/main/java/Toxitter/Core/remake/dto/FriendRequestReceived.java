package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.realtime.TransferrableDataAtom;
import Toxitter.Model.ReservoirEntity;
import com.google.gson.Gson;

@PushTo(route = "receiveFriendRequest", method = "notifyFriendRequestReceived")
public class FriendRequestReceived implements TransferrableDataAtom
{
    public String fromUserName;
    public String fromUserId;

    @Override
    public String asJSON()
    {
        return new Gson().toJson(this).toString();
    }

    @Override
    public String asJavaScript() {
        return null;
    }

    @Override
    public String asJavaCode() {
        return null;
    }
}