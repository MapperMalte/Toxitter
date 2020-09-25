package Toxitter.Core.realtime;

import Toxitter.Core.UserReservoir;
import Toxitter.Core.annotations.FetchAt;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import Toxitter.Core.remake.dto.ChatMessage;
import Toxitter.Security.annotations.Protected;

@FetchAt(route = "chat")
public class Chat
{
    @Route(route = "send")
    @Protected(scope = "user")
    public static String incomingMessageFromUser(
            @RequestParam(name = "fromUserId", obligatory = true) String fromUserId,
            @RequestParam(name = "targetUserName", obligatory = true) String targetUserName,
            @RequestParam(name = "chatmessage", obligatory = true) String chatMessage)
    {
        ChatMessage chatMessage1 = new ChatMessage();
        chatMessage1.fromUserId = fromUserId;
        chatMessage1.fromUserName = UserReservoir.getUserByUserId(fromUserId).name;
        chatMessage1.message = chatMessage;

        ToxitterWebsocketHandler.push(UserReservoir.getUserByUserName(targetUserName).userId,chatMessage1);
        return "Chatmessage delivered!";
    }
}