package Avatar.Elemental.earth.persistence.mysql;

import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.water.QueueSleeper;

public class RelationQueueSleeper extends QueueSleeper<ID, ReservoirEntityList>
{
    @Override
    public void putToSleep(ID key, ReservoirEntityList value) {
        value.bottom();
        while (!value.isPointerNull())
        {
            value.next();
        }
    }

    @Override
    public ReservoirEntityList wakeup(ID key) {
        return null;
    }

    @Override
    public void delete(ID key)
    {

    }
}
