package Toxitter.Core.remake;

import Toxitter.Core.User;
import Toxitter.Core.realtime.OnlineStateManager;
import Toxitter.Core.realtime.TransferrableDataAtom;

public class Pusher
{
    public static void push(TransferrableDataAtom outputDTO, User toUser)
    {
        OnlineStateManager.getWebsocketByUserId(toUser.userId).send(outputDTO.asJSON());
    }
}
