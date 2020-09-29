package Toxitter.Model.elemental.sky;

import Toxitter.Model.elemental.matter.ID;
import Toxitter.Model.elemental.matter.ReservoirEntity;
import Toxitter.Model.elemental.matter.ReservoirEntityList;

import java.util.TreeMap;

public class Autorelation<V extends ReservoirEntity>
{
    TreeMap<ID, ReservoirEntityList<V>> data = new TreeMap<>();

    public void link(V value1, V value2)
    {
        if ( !data.containsKey(value1.getId()) ) data.put(value1.getId(),new ReservoirEntityList<>());
        if ( !data.containsKey(value2.getId()) ) data.put(value1.getId(),new ReservoirEntityList<>());
        data.get(value1.getId()).addOnTop(value2);
        data.get(value2.getId()).addOnTop(value1);
    }

    public boolean hasLink(V value1, V value2)
    {
        if ( !data.containsKey(value1.getId()) ) return false;
        return data.get(value1.getId()).movePointerToKey(value2.getId());
    }

    /**
     * All nodes leading to this
     * @param key
     * @return
     */
    public ReservoirEntityList<V> getLinked(ID key)
    {
        return data.get(key);
    }
}
