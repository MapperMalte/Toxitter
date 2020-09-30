package Toxitter.Core.Elemental.earth;

import Toxitter.Core.Elemental.wind.TemporalQueue;

public class Reservoir<V extends ReservoirEntity>
{
    private TemporalQueue<ID, ReservoirEntity> data;

    public void put(ID id, V reservoirEntity)
    {
        if ( ! data.exists(id) );
        data.put(id,null);
    }

    public ReservoirEntity get(ID key)
    {
        return data.get(key);
    }
}
