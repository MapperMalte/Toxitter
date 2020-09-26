package Toxitter.Core.remake;

import Toxitter.Core.User;
import Toxitter.Core.realtime.OnlineStateManager;
import Toxitter.Core.remake.dto.output.Transferrable;

public class Pusher
{
    public static void push(Transferrable outputDTO, User toUser)
    {
        OnlineStateManager.getWebsocketByUserId(toUser.userId).send(outputDTO.asJSON());
    }
}
