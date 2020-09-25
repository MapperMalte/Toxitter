package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.annotations.TeleKeyFragment;
import Toxitter.Core.realtime.Transferrable;
import com.google.gson.Gson;

@PushTo(route = "/receiveChatMessage", method = "chatMessageReceived")
public class ChatMessage implements Transferrable
{
    @TeleKeyFragment
    public String chatId;
    public String fromUserName;
    public String fromUserId;
    public String message;

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