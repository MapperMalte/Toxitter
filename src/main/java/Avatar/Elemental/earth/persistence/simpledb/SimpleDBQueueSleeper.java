package Avatar.Elemental.earth.persistence.simpledb;

import Avatar.Elemental.water.QueueSleeper;

public class SimpleDBQueueSleeper<K extends Comparable, V> extends QueueSleeper<K, V>
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
