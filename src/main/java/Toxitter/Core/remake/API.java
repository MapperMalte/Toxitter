package Toxitter.Core.remake;

import Toxitter.Core.annotations.FetchAt;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Security.annotations.Protected;

public class API
{
    @FetchAt(route = "/friends/add")
    @Protected(scope = "user")
    public static String sendFriendRequest(
            @RequestParam(name = "username", obligatory = true) String username
    )
    {
        return null;
    }
}
