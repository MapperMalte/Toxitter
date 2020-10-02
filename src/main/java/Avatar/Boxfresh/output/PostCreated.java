package Avatar.Boxfresh.output;

import Avatar.Annotations.core.PushTo;
import Avatar.Elemental.water.OUTPUT;

@PushTo(route = "/postCreated", method = "notifyPostCreated")
public class PostCreated extends OUTPUT
{
    public String msg = "";
}
