package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.realtime.TransferrableDataAtom;

@PushTo(route = "/notifyFriendRequestAccepted", method = "notifyFriendRequestAccepted")
public class FriendRequestAccepted implements TransferrableDataAtom
{
    public String from;

    @Override
    public String asJSON() {
        return "{\"from\" : \""+from+"\"}";
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
