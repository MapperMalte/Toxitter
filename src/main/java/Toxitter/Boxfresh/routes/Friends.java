package Toxitter.Boxfresh.routes;

import Toxitter.Annotations.core.FetchAt;
import Toxitter.Core.realtime.ToxitterWebsocketHandler;
import Toxitter.Annotations.core.Route;
import Toxitter.Boxfresh.output.FriendRequestAccepted;
import Toxitter.Boxfresh.output.FriendRequestReceived;
import Toxitter.Model.elemental.matter.ReservoirEntity;
import Toxitter.Annotations.security.Protected;

@FetchAt(route = "friends")
public class Friends
{

    private static class ReceivedFriendRequest extends ReservoirEntity {
        String id;

        FriendRequestReceived friendRequestReceived;

        public ReceivedFriendRequest(FriendRequestReceived friendRequestReceived) {
            this.friendRequestReceived = friendRequestReceived;
        }

    }

    @Protected(scope = "user")
    @Route(route = "add")
    public static String sendFriendRequest(
            String fromUserId,
            String targetUserName
    ) {

        String targetUserId = UserReservoir.getUserIdByMail(targetUserName);
        FriendRequestReceived friendRequestReceived = new FriendRequestReceived();
        friendRequestReceived.fromUserId = fromUserId;
        friendRequestReceived.fromUserName = UserReservoir.getUserByUserId(fromUserId).name;

        ReceivedFriendRequest receivedFriendRequest = new ReceivedFriendRequest(friendRequestReceived);

        ToxitterWebsocketHandler.push(targetUserId, friendRequestReceived);
        return "Friend request sent!";
    }

    @Protected(scope = "user")
    @Route(route = "accept")
    public static String acceptFriendRequest(
            String fromUserName,
            String acceptingUserId
    ) {
        String requestSentFromUserId = UserReservoir.getUserByUserName(fromUserName).userId;
        //ReceivedFriendRequest receivedFriendRequest = sentRequests.getget(requestSentFromUserId,acceptingUserId);
        /*FriendRequestAccepted answer2 = new FriendRequestAccepted();
        answer2.from = fromUserName;
        ToxitterWebsocketHandler.push(receivedFriendRequest.friendRequestReceived.fromUserId, answer2);
        */
        FriendRequestAccepted answer = new FriendRequestAccepted();
        answer.from = UserReservoir.getUserByUserId(acceptingUserId).name;
        ToxitterWebsocketHandler.push(requestSentFromUserId, answer);
        return "Freundschaft Angenommen!";
    }
}