package theory;

import java.util.TreeMap;

public class TemporalQueue<K extends Comparable,V>
{
    // Größe Null: Alles wird sofort schlafen gelegt und vom Schlaf
    // recovert
    private ValueBag bottom = null;
    private ValueBag top = null;
    private int size = 0;
    private int capacity = 100;
    private QueueSleeper<K,V> queueSleeper;

    private TreeMap<K,ValueBag> index = new TreeMap<>();

    private class ValueBag
    {
        V value;
        K key;
        ValueBag next;
        ValueBag previous;
    }

    public TemporalQueue(int size, QueueSleeper<K,V> onExileFromQueue)
    {
        this.capacity = size;
        this.queueSleeper = onExileFromQueue;
    }

    private void pullToTop(ValueBag vb)
    {
        // zero elements
        if ( top == null)
        {
            top = vb;
            return;
        }
        // exactly one element
        if (  top == bottom )
            return;
        // already top of list
        else if ( vb == top ){
            return;
        } else if ( vb == bottom )
        {
            vb.next.previous = null;
            top.next = vb;
            vb.previous = top;
            vb.next = null;
            top = vb;
        } else {
            // has next and previous
            vb.next.previous = vb.previous;
            vb.previous.next = vb.next;
        }
    }

    private void overflow()
    {
        while ( size > capacity )
        {
            if ( bottom == null ) return;
            this.queueSleeper.putToSleep(bottom.key, bottom.value);

            if ( !(bottom == null || bottom.next == null) )
                bottom.next.previous = null;

            bottom = bottom.next;
            size--;
        }
    }

    public void put(K key, V value)
    {
        if ( !index.containsKey(key) )
        {
            ValueBag vb = new ValueBag();
            vb.value = value;
            vb.key = key;
            if ( top == null )
            {
                top = vb;
                bottom = vb;
            } else {
                top.next = vb;
                vb.previous = top;
            }
            index.put(key,vb);
            size++;
            overflow();
        } else {
            ValueBag vb = index.get(key);
            vb.value = value;
            pullToTop(vb);
        }
    }

    public V get(K key)
    {
        if ( !index.containsKey(key) )
        {
            return null;
        }
        ValueBag vb = index.get(key);
        pullToTop(vb);
        return vb.value;
    }

    public void delete(K key)
    {
        if ( index.containsKey(key) )
        {
            ValueBag vb = index.get(key);
            if ( vb == bottom )
            {
                if ( ! (vb.next == null) )
                {
                    vb.next.previous = null;
                    bottom = vb.next;
                }
                vb.next = null;
            } else if ( vb == top )
            {
                top.previous.next = null;
                top = top.previous;
            } else {
                vb.next.previous = vb.previous;
                vb.previous.next = vb.next;
            }
            index.remove(key);
        }
    }

    public boolean exists(K key)
    {
        return index.containsKey(key);
    }

    public void shutdown()
    {

    }
}