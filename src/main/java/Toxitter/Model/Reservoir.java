package Toxitter.Model;

import Toxitter.Boxfresh.NirvanaQueueSleeper;
import Toxitter.Persistence.persistence.simpledb.SimpleDBQueueSleeper;

public class Reservoir
    <
        K extends Comparable,
        K2 extends Comparable,
        V extends ReservoirEntity<K2>>

{
    public static int PERSIST_LIKE = Reservoir.RESERVOIR_PERSISTENCE_WIPE_ALL;
    public static int CACHED_ELEMENT_COUNT = 10000;

    public static final int RESERVOIR_PERSISTENCE_WIPE_ALL = 0;
    public static final int RESERVOIR_PERSISTENCE_SIMPLE_DB = 1;
    public static final int RESERVOIR_PERSISTENCE_MYSQL = 2;

    private OneToMany<K, K2, V> oneToMany;

    public Reservoir()
    {
        switch (PERSIST_LIKE)
        {
            case RESERVOIR_PERSISTENCE_WIPE_ALL:
                oneToMany = new OneToMany<K,K2,V>(
                        Reservoir.CACHED_ELEMENT_COUNT,
                        new NirvanaQueueSleeper<K,ReservoirEntityList<K2,V>>()
                );
            case RESERVOIR_PERSISTENCE_SIMPLE_DB:
                oneToMany = new OneToMany<K,K2,V>(
                        Reservoir.CACHED_ELEMENT_COUNT,
                        new SimpleDBQueueSleeper<K,ReservoirEntityList<K2,V>>()
                );
        }
    }

    public void addById(K key, V value)
    {
        oneToMany.put(key,value);
    }

    public ReservoirEntityList<K2,V> getAllById(K key)
    {
        return oneToMany.get(key);
    }

    public ReservoirEntity<K2> getget(K firstKey, K2 secondKey)
    {
        return oneToMany.getget(firstKey, secondKey);
    }

    public void delete(K key)
    {
        oneToMany.delete(key);
    }

    public void deletedelete(K firstkey, K2 secondKey)
    {
        oneToMany.deletedelete(firstkey, secondKey);
    }
}
