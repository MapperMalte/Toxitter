package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.realtime.Transferrable;

@PushTo(route = "/incomingLikeInfo", method = "notifyIncomingLike")
public class Like implements Transferrable
{
    String contentId;
    String reactionId;
    String likingUserUserId;

    @Override
    public String asJSON() {
        return null;
    }

    @Override
    public String asJavaScript() {
        return null;
    }

    @Override
    public String asJavaCode() {
        return null;
    }
}
