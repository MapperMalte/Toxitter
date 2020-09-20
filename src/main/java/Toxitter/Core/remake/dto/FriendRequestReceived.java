package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.realtime.OutputDTO;
import com.google.gson.Gson;

@PushTo(route = "receiveFriendRequest", method = "notifyFriendRequestReceived")
public class FriendRequestReceived extends OutputDTO
{
    public String method = "notifyFriendRequestReceived";
    public String route = "receiveFriendRequest";
    public String fromUserName;
    public String fromUserId;

    @Override
    public String asJSON()
    {
        StringBuilder output = new StringBuilder();
        output.append("/receiveFriendRequest : ");
        output.append(new Gson().toJson(this));
        return output.toString();
    }
}