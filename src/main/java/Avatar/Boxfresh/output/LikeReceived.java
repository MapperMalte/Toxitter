package Avatar.Boxfresh.output;

import Avatar.Annotations.core.PushTo;
import Avatar.Elemental.water.OUTPUT;

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