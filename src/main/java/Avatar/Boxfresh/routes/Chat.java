package Avatar.Boxfresh.routes;

import Avatar.Annotations.core.FetchAt;
import Avatar.Boxfresh.reservoirs.UserReservoir;
import Avatar.Elemental.aether.ToxitterWebsocketHandler;
import Avatar.Annotations.core.Route;
import Avatar.Boxfresh.output.ChatMessage;
import Avatar.Annotations.security.Protected;

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

        ToxitterWebsocketHandler.get().push(UserReservoir.getUserByUserName(targetUserName),chatMessage1);
        return "Chatmessage delivered!";
    }
}