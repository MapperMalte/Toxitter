package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;

@PushTo(route = "/notify/friendRequestAccepted", method = "notifyFriendRequestAccepted")
public class FriendRequestAccepted
{
    private String from;
}
