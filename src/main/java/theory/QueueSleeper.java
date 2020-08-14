package theory;

import theory.DiamondList;

public abstract class QueueSleeper<K extends Comparable, V>
{
    public abstract void putToSleep(K key, V value);
    public abstract void multiPutToSleep(DiamondList<V> values);
    public abstract V wakeup(K key);
    public abstract void delete(K key);
}
