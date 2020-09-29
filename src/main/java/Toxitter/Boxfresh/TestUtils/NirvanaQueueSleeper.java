package Toxitter.Boxfresh.TestUtils;

import Toxitter.Model.elemental.wind.QueueSleeper;

public class NirvanaQueueSleeper<K extends Comparable, V> extends QueueSleeper<K, V>
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