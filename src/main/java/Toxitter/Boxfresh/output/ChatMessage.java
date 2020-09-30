package Toxitter.Boxfresh.output;

import Toxitter.Annotations.core.PushTo;
import Toxitter.Elemental.water.OUTPUT;
import com.google.gson.Gson;

@PushTo(route = "/receiveChatMessage", method = "chatMessageReceived")
public class ChatMessage extends OUTPUT
{
    public String chatId;
    public String fromUserName;
    public String fromUserId;
    public String message;

    @Override
    public String asJSON()
    {
        return new Gson().toJson(this).toString();
    }
}