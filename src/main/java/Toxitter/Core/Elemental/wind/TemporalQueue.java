package Toxitter.Core.Elemental.wind;

import Toxitter.Core.Elemental.water.QueueSleeper;

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
            bottom = bottom.next;
            vb.next.previous = null;
            top.next = vb;
            vb.previous = top;
            vb.next = null;
            top = vb;
        } else {
            if ( !(vb.next == null) )
            {
                // has next and previous
                vb.next.previous = vb.previous;
                vb.previous.next = vb.next;
            }

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

            index.remove(bottom.key);
            bottom = bottom.next;
            size--;
        }
    }
    public String print()
    {
        ValueBag bot = bottom;
        StringBuilder sb = new StringBuilder();

        while ( !(bot == null) )
        {
            sb.append(bot.key.toString()).append(" : ").append(bot.value.toString()).append("\n");
            bot = bot.next;
        }

        return sb.toString();
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
                top = vb;
                if ( (bottom.next == null) )
                {
                    bottom.next = vb;
                }
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
            return queueSleeper.wakeup(key);
        }
        ValueBag vb = index.get(key);
        pullToTop(vb);
        return vb.value;
    }

    /**
     * Deletes the element if it exists, then calls the queueSleepers delete method
     * @param key
     */
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
        queueSleeper.delete(key);
    }

    public V getTop()
    {
        return top.value;
    }

    public V getBottom()
    {
        return bottom.value;
    }

    public boolean exists(K key)
    {
        return index.containsKey(key);
    }

    /**
     * Puts all values in the queue to sleep via QueueSleeper
     */
    public void shutdown()
    {
        ValueBag bot = bottom;
        while ( !(bot == null) )
        {
            queueSleeper.putToSleep(bot.key,bot.value);
            bot = bot.next;
        }
    }
}