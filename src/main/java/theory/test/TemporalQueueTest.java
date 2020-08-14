package theory.test;

import Toxitter.Model.ID;
import org.junit.Test;
import theory.DiamondList;
import theory.QueueSleeper;
import theory.TemporalQueue;

import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TemporalQueueTest
{
    private class MockQueueSleeper<K extends Comparable, V> extends QueueSleeper<K, V>
    {
        private TreeMap<K,V> data = new TreeMap<>();
        @Override
        public void putToSleep(K key, V value) {
            System.out.println("Sleep "+value.toString());
            data.put(key, value);
        }

        @Override
        public void multiPutToSleep(DiamondList<V> values) {

        }

        @Override
        public V wakeup(K key)
        {
            return data.get(key);
        }

        @Override
        public void delete(K key)
        {
            data.remove(key);
        }
    }
    @Test
    public void testPutAndGet()
    {
        TemporalQueue<String, String> tq = new TemporalQueue<>(110, new MockQueueSleeper<String,String>());
        tq.put("Hallo","Welt");
        tq.put("Dreissig","Euro");
        tq.put("123","123");
        for(int i = 0; i < 100; i++)
        {
            tq.put(""+i,"#"+i);
        }
        assertEquals(tq.get("Hallo"),"Welt");
        assertEquals(tq.get("123"),"123");
        assertEquals(tq.get("Dreissig"),"Euro");
        for(int i = 0; i < 100; i++)
        {
            assertEquals(tq.get(""+i),"#"+i);
        }
    }

    @Test
    public void testDelete()
    {
        TemporalQueue<String, String> tq = new TemporalQueue<>(1, new MockQueueSleeper<String,String>());

        tq.put("Hallo","Welt");
        tq.delete("Hallo");
        assertNull(tq.get("Hallo"));

        tq.put("1","1");
        tq.put("2","2");
        System.out.println(tq.print());
        tq.delete("1");
        assertNull(tq.get("1"));
    }

    @Test
    public void testGetPutsElemnentToTopOfTemporalQueue()
    {
        TemporalQueue<String, String> tq = new TemporalQueue<>(5, new MockQueueSleeper<String,String>());
        tq.put("1","1");
        tq.put("2","2");
        tq.put("3","3");
        tq.put("4","4");
        tq.get("1");
        assertEquals(tq.print(),"2 : 2\n" +
                "3 : 3\n" +
                "4 : 4\n" +
                "1 : 1\n");
        tq.get("2");
        assertEquals(tq.print(),"3 : 3\n" +
                "4 : 4\n" +
                "1 : 1\n" +
                "2 : 2\n");
        tq.get("3");
        assertEquals(tq.print(),"4 : 4\n" +
                "1 : 1\n" +
                "2 : 2\n" +
                "3 : 3\n");
    }
}
