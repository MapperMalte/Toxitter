package Avatar.Boxfresh.output;

import Avatar.Boxfresh.routes.Like;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.water.OUTPUT;
import Avatar.Elemental.water.html.annotations.Consumes;

public class LikeSignature extends OUTPUT
{

    @Consumes(type = LikeReceived.class)
    private ReservoirEntityList<Like> likes;
}