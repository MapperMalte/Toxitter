package Toxitter.Core.realtime;

import Toxitter.Boxfresh.NirvanaQueueSleeper;
import Toxitter.Core.User;
import Toxitter.Core.UserReservoir;
import Toxitter.Core.annotations.FetchAt;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import Toxitter.Core.remake.dto.FriendRequestAccepted;
import Toxitter.Core.remake.dto.FriendRequestReceived;
import Toxitter.Model.ID;
import Toxitter.Model.OneToMany;
import Toxitter.Model.ReservoirEntity;
import Toxitter.Security.annotations.Protected;

@FetchAt(route = "friends")
public class Friends {
    private static OneToMany<String, String, ReceivedFriendRequest> sentRequests = new OneToMany<>(100, new NirvanaQueueSleeper<>());

    private static class ReceivedFriendRequest extends ReservoirEntity<String> {
        String id;

        FriendRequestReceived friendRequestReceived;

        public ReceivedFriendRequest(FriendRequestReceived friendRequestReceived) {
            this.friendRequestReceived = friendRequestReceived;
        }

    }

    @Protected(scope = "user")
    @Route(route = "add")
    public static String sendFriendRequest(
            @RequestParam(name = "fromUserId", obligatory = true) String fromUserId,
            @RequestParam(name = "targetUserName", obligatory = true) String targetUserName
    ) {

        String targetUserId = UserReservoir.getUserIdByMail(targetUserName);
        FriendRequestReceived friendRequestReceived = new FriendRequestReceived();
        friendRequestReceived.fromUserId = fromUserId;
        friendRequestReceived.fromUserName = UserReservoir.getUserByUserId(fromUserId).name;

        ReceivedFriendRequest receivedFriendRequest = new ReceivedFriendRequest(friendRequestReceived);
        sentRequests.putput(fromUserId, targetUserId, receivedFriendRequest);

        ToxitterWebsocketHandler.push(targetUserId, friendRequestReceived);
        return "Friend request sent!";
    }

    @Protected(scope = "user")
    @Route(route = "accept")
    public static String acceptFriendRequest(
            @RequestParam(name = "fromUserName", obligatory = true) String fromUserName,
            @RequestParam(name = "acceptingUserId", obligatory = true) String acceptingUserId
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