package Toxitter.Model.elemental.matter;

import Toxitter.Model.elemental.matter.ID;
import Toxitter.Model.elemental.matter.ReservoirEntity;
import Toxitter.Model.elemental.matter.ReservoirEntityList;
import Toxitter.Model.elemental.sky.TemporalQueue;

import java.util.TreeMap;

public class Relation<V extends ReservoirEntity, T extends ReservoirEntity>
{
    // Queue-Sleeper: Save only ID-connections, only relation.
    // Table: CLASS_NAME, ID_ONE, ID_TWO
    TemporalQueue<ID, ReservoirEntityList<T>> forward;
    TemporalQueue<ID,ReservoirEntityList<V>> backward;

    TemporalQueue<ID,ReservoirEntityList<ID>> backwardX;

    public void put(V value1, T value2)
    {
        if ( !backward.exists(value2.getId()) ) backward.put(value2.getId(),new ReservoirEntityList<>());
        if ( !forward.exists(value1.getId()) ) forward.put(value1.getId(),new ReservoirEntityList<>());
        forward.get(value1.getId()).addOnTop(value2);
        backward.get(value2.getId()).addOnTop(value1);
    }

    public boolean hasLink(V value1, T value2)
    {
        if ( !backward.exists(value1.getId()) ) return false;
        return backward.get(value1.getId()).movePointerToKey(value2.getId());
    }

    /**
     * All nodes leading to this
     * @param key
     * @return
     */
    public ReservoirEntityList<T> forwardGet(V key)
    {
        return forward.get(key.getId());
    }

    public ReservoirEntityList<V> backwardGet(T key)
    {
        return backward.get(key.getId());
    }

}
