package Toxitter.Persistence.persistence.simpledb;

import Toxitter.Model.ID;
import Toxitter.Model.ReservoirEntity;
import theory.QueueSleeper;

public class SimpleDBQueueSleeper<K extends ID, V extends ReservoirEntity> extends QueueSleeper<K, V>
{
    @Override
    public void putToSleep(K key, V value) {

    }

    @Override
    public V wakeup(K key) {
        return null;
    }

    @Override
    public void delete(K key) {

    }
}
