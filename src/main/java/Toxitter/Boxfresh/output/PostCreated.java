package Toxitter.Boxfresh.output;

import Toxitter.Annotations.core.PushTo;
import Toxitter.Elemental.water.OUTPUT;

@PushTo(route = "/postCreated", method = "notifyPostCreated")
public class PostCreated extends OUTPUT
{
    public String msg = "";
}
