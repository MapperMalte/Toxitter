package Toxitter.Core.Elemental.earth.persistence.mysql;

import Toxitter.Core.Elemental.earth.ID;
import Toxitter.Core.Elemental.earth.ReservoirEntityList;
import Toxitter.Core.Elemental.water.QueueSleeper;

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
