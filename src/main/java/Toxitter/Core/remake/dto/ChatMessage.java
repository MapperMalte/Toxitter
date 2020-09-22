package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.realtime.TransferrableDataAtom;
import com.google.gson.Gson;

@PushTo(route = "/receiveChatMessage", method = "chatMessageReceived")
public class ChatMessage extends TransferrableDataAtom
{
    public String fromUserName;
    public String fromUserId;
    public String message;

    @Override
    public String asJSON()
    {
        StringBuilder output = new StringBuilder();
        output.append("/receiveChatMessage ");
        output.append(new Gson().toJson(this));
        return output.toString();
    }
}