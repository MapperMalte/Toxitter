package Toxitter.Model.theory;

public class TemporalQueue<K extends Comparable,V>
{
    // Größe Null: Alles wird sofort schlafen gelegt und vom Schlaf
    // recovert

    public TemporalQueue(int size, QueueSleeper<K,V> onExileFromQueue)
    {

    }

    public void put(K key, V value)
    {

    }

    public V get(K key)
    {
        return null;
    }

    public void delete(K key)
    {

    }

    public boolean exists(K key)
    {
        return false;
    }

    public void shutdown()
    {

    }
}