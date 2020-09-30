package Toxitter.Boxfresh.output;

import Toxitter.Annotations.core.PushTo;
import Toxitter.Core.Elemental.water.OUTPUT;

@PushTo(route = "/incomingLikeInfo", method = "notifyIncomingLike")
public class LikeReceived extends OUTPUT
{
    String contentId;
    String reactionId;
    String likingUserUserId;

    @Override
    public String asJSON() {
        return null;
    }
}
