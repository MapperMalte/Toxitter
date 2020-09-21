package Toxitter.Core.remake;

import Toxitter.Core.User;
import Toxitter.Core.realtime.Online;
import Toxitter.Core.realtime.OutputDTO;

public class Pusher
{
    public static void push(OutputDTO outputDTO, User toUser)
    {
        Online.getWebsocketByUserId(toUser.userId).send(outputDTO.asJSON());
    }
}
