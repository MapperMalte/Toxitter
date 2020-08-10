package Toxitter.Persistence.test;

import Toxitter.Persistence.OneToOne;
import Toxitter.Persistence.annotations.Table;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OneToOneTest
{
    @Test
    public void testPutAndRead()
    {
        OneToOne<String,Integer> ids = new OneToOne<>();
        ids.put("123",123);
        ids.put("abc",10);
        assertEquals(ids.read("123").intValue(),123);
        assertEquals(ids.read("abc").intValue(),10);
    }

    @Test
    public void testPutUpdateAndRead()
    {
        OneToOne<String,Integer> ids = new OneToOne<>();
        ids.put("123",123);
        ids.put("abc",10);
        ids.update("abc",100);
        assertEquals(ids.read("123").intValue(),123);
        assertEquals(ids.read("abc").intValue(),100);
    }

    @Test
    public void testPutAndDelete()
    {
        OneToOne<String,Integer> ids = new OneToOne<>();
        ids.put("123",123);
        ids.put("abc",10);
        ids.delete("abc");
        assertEquals(ids.read("123").intValue(),123);
        assertNull(ids.read("abc"));
    }

}
