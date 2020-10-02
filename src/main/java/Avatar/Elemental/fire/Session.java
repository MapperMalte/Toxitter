package Avatar.Elemental.fire;

import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntity;

import java.util.TreeMap;

public class Session
{
    private long lastUserInteraction;
    private ID userId;
    private TreeMap<ID, UserReactionToReservoirEntity> userReactionByContentId = new TreeMap<>();
    private ReservoirEntity[] set;

    public void like(ReservoirEntity entity)
    {
        userReactionByContentId.put(entity.getId(),UserReactionToReservoirEntity.LIKE);
    }

    // Report result to homunclus and trigger backpropagation
    private void finishSession()
    {

    }
}
