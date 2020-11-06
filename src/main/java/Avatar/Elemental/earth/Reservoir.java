package Avatar.Elemental.earth;

import Avatar.Elemental.wind.artifacts.TemporalQueue;

public class Reservoir<V extends ReservoirEntity>
{
    private TemporalQueue<ID, V> data;

    public void put(ID id, V reservoirEntity)
    {
        if ( ! data.exists(id) );
        data.put(id,null);
    }

    public V get(ID key)
    {
        return data.get(key);
    }
}
