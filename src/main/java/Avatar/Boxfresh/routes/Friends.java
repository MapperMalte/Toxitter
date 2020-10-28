package Avatar.Boxfresh.routes;

import Avatar.Annotations.core.FetchAt;
import Avatar.Elemental.aether.ToxitterWebsocketHandler;
import Avatar.Annotations.core.Route;
import Avatar.Boxfresh.output.FriendRequestAccepted;
import Avatar.Boxfresh.output.FriendRequestReceived;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Annotations.security.Protected;
import Avatar.Elemental.water.Pool;

@FetchAt(route = "friends")
public class Friends
{
    private static Pool<FriendRequestReceived> requestReceivedPool;

    private static class ReceivedFriendRequest extends ReservoirEntity {
        String id;

        FriendRequestReceived friendRequestReceived;

        public ReceivedFriendRequest(FriendRequestReceived friendRequestReceived) {
            this.friendRequestReceived = friendRequestReceived;
        }

    }

    @Protected(scope = "user")
    @Route(route = "add")
    public static String sendFriendRequestFromTo(
            String fromUserId,
            String targetUserName
    ) {

        String targetUserId = UserReservoir.getUserIdByMail(targetUserName);

        FriendRequestReceived friendRequestReceived = new FriendRequestReceived();
        friendRequestReceived.fromUserId = fromUserId;
        friendRequestReceived.fromUserName = UserReservoir.getUserByUserId(fromUserId).name;

        ReceivedFriendRequest receivedFriendRequest = new ReceivedFriendRequest(friendRequestReceived);

        requestReceivedPool.push(friendRequestReceived);

        ToxitterWebsocketHandler.get().push(targetUserId, friendRequestReceived);

        return "Friend request sent!";
    }

    @Protected(scope = "user")
    @Route(route = "accept")
    public static String acceptFriendRequestFromBy(
            String fromUserName,
            String acceptingUserId
    ) {
        String requestSentFromUserId = UserReservoir.getUserByUserName(fromUserName).userId;

        FriendRequestAccepted answer = new FriendRequestAccepted();
        answer.from = UserReservoir.getUserByUserId(acceptingUserId).name;
        ToxitterWebsocketHandler.get().push(requestSentFromUserId, answer);

        return "Freundschaft Angenommen!";
    }
}