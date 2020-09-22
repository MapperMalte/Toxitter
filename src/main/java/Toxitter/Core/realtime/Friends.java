package Toxitter.Core.realtime;

import Toxitter.Boxfresh.NirvanaQueueSleeper;
import Toxitter.Core.User;
import Toxitter.Core.UserReservoir;
import Toxitter.Core.annotations.FetchAt;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import Toxitter.Core.realtime.ToxitterWebsocketHandler;
import Toxitter.Core.remake.dto.ChatMessage;
import Toxitter.Core.remake.dto.FriendRequestReceived;
import Toxitter.Model.OneToMany;
import Toxitter.Model.OneToOne;
import Toxitter.Security.annotations.Protected;

@FetchAt(route = "friends")
public class Friends
{
    private static OneToMany<String, String, User> sentRequests = new OneToMany<>(100, new NirvanaQueueSleeper<>());

    @Protected(scope = "user")
    @Route(route = "add")
    public static String sendFriendRequest(
            @RequestParam(name = "fromUserId", obligatory = true) String fromUserId,
            @RequestParam(name = "targetUserName", obligatory = true) String targetUserName
    )
    {

        sentRequests.put(fromUserId, UserReservoir.getUserByMail(targetUserName));
        String targetUserId = UserReservoir.getUserIdByMail(targetUserName);
        FriendRequestReceived friendRequestReceived = new FriendRequestReceived();
        friendRequestReceived.fromUserId = fromUserId;
        friendRequestReceived.fromUserName = UserReservoir.getUserByUserId(fromUserId).name;

        ToxitterWebsocketHandler.push(targetUserId,friendRequestReceived);
        return "Friend request sent!";
    }

    @Protected(scope = "user")
    @Route(route = "accept")
    public static String acceptFriendRequest(
            @RequestParam(name = "username", obligatory = true) String username
    )
    {
        return null;
    }

}
