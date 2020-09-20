package Toxitter.Core.remake;

import Toxitter.Core.User;
import Toxitter.Core.UserReservoir;
import Toxitter.Core.annotations.FetchAt;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import Toxitter.Model.OneToMany;
import Toxitter.Model.OneToOne;
import Toxitter.Security.annotations.Protected;

@FetchAt(route = "friends")
public class Friends
{
    private static OneToMany<String, String, User> sentRequests;

    @Protected(scope = "user")
    @Route(route = "add")
    public static String sendFriendRequest(
            @RequestParam(name = "fromUserId", obligatory = true) String fromUserId,
            @RequestParam(name = "targetUserId", obligatory = true) String targetUserId
    )
    {
        sentRequests.put(fromUserId, UserReservoir.getUserByUserId(targetUserId));
        return "";
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
