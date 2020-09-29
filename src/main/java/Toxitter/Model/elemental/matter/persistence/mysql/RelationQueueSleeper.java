package Toxitter.Model.elemental.matter.persistence.mysql;

import Toxitter.Model.elemental.matter.ID;
import Toxitter.Model.elemental.matter.Relation;
import Toxitter.Model.elemental.matter.ReservoirEntityList;
import Toxitter.Model.elemental.wind.QueueSleeper;

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
