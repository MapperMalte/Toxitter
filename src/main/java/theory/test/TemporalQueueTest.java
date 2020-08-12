package theory.test;

import Toxitter.Model.ID;
import org.junit.Test;
import theory.DiamondList;
import theory.QueueSleeper;
import theory.TemporalQueue;

import static org.junit.Assert.assertEquals;

public class TemporalQueueTest
{
    private class MockQueueSleeper<K extends Comparable, V> extends QueueSleeper<K, V>
    {
        @Override
        public void putToSleep(K key, V value) {
            System.out.println("Sleep "+value.toString());
        }

        @Override
        public void multiPutToSleep(DiamondList<V> values) {

        }

        @Override
        public V wakeup(K key) {
            return null;
        }
    }
    @Test
    public void test()
    {
        TemporalQueue<String, String> tq = new TemporalQueue<>(50, new MockQueueSleeper<String,String>());
        tq.put("Hallo","Welt");
        tq.put("Dreissig","Euro");
        tq.put("123","123");
        for(int i = 0; i < 100; i++)
        {
            tq.put(new ID().makeID(),"123");
        }
        assertEquals(tq.get("Hallo"),"Welt");
        assertEquals(tq.get("123"),"123");
        assertEquals(tq.get("Dreissig"),"Euro");
    }
}
