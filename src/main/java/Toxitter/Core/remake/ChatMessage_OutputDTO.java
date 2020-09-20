package Toxitter.Core.remake;

import Toxitter.Core.annotations.PushAt;
import com.google.gson.Gson;

@PushAt(route = "/receiveChatMessage")
public class ChatMessage_OutputDTO
{
    public String fromUserName;
    public String fromUserId;
    public String message;
    public String method = "chatMessageReceived";

    public static String push(ChatMessage_OutputDTO chatMessage_outputDTO)
    {
        StringBuilder output = new StringBuilder();
        output.append("/receiveChatMessage ");
        output.append(new Gson().toJson(chatMessage_outputDTO));
        return output.toString();
    }
}
