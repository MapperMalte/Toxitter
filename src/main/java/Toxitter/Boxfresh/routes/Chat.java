package Toxitter.Boxfresh.routes;

import Toxitter.Annotations.core.FetchAt;
import Toxitter.Elemental.aether.ToxitterWebsocketHandler;
import Toxitter.Annotations.core.Route;
import Toxitter.Boxfresh.output.ChatMessage;
import Toxitter.Annotations.security.Protected;

@FetchAt(route = "chat")
public class Chat
{
    @Route(route = "send")
    @Protected(scope = "user")
    public static String incomingMessageFromUser(
            String fromUserId,
            String targetUserName,
            String chatMessage)
    {
        ChatMessage chatMessage1 = new ChatMessage();
        chatMessage1.fromUserId = fromUserId;
        chatMessage1.fromUserName = UserReservoir.getUserByUserId(fromUserId).name;
        chatMessage1.message = chatMessage;

        ToxitterWebsocketHandler.push(UserReservoir.getUserByUserName(targetUserName).userId,chatMessage1);
        return "Chatmessage delivered!";
    }
}