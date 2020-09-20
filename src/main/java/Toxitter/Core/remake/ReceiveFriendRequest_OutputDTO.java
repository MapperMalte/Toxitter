package Toxitter.Core.remake;

import Toxitter.Core.annotations.PushAt;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@PushAt(route = "/receiveFriendRequest")
public class ReceiveFriendRequest_OutputDTO
{
    String fromUserName;
    String fromUserId;

    public static String push(ReceiveFriendRequest_OutputDTO receiveFriendRequest_outputDTO)
    {
        StringBuilder output = new StringBuilder();
        output.append("/receiveFriendRequest : ");
        output.append(new Gson().toJson(receiveFriendRequest_outputDTO));
        return output.toString();
    }
}