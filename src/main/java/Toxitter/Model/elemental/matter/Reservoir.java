package Toxitter.Model.elemental.matter;

import Toxitter.Model.elemental.sky.TemporalQueue;

public class Reservoir<V extends ReservoirEntity>
{
    private TemporalQueue<ID, ReservoirEntity> data;

    public void put(ReservoirEntity reservoirEntity)
    {
        data.put(reservoirEntity.getId(),reservoirEntity);
    }

    public ReservoirEntity get(ID key)
    {
        return data.get(key);
    }
}
