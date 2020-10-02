package Avatar.Elemental.water;

public abstract class QueueSleeper<K extends Comparable, V>
{
    public abstract void putToSleep(K key, V value);
    public abstract V wakeup(K key);
    public abstract void delete(K key);
}