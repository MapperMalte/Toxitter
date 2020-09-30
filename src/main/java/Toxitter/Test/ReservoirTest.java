package Toxitter.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ReservoirTest
{
    /*
    @Test
    public void testPutAndRead()
    {
        Reservoir<String,TestReservoirEntity> ids = new Reservoir<>(10, new NirvanaQueueSleeper<>());

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);

        ids.put(malte);
        ids.put(gott);

        TestReservoirEntity restoredMalte = ids.read(malte.getId());
        TestReservoirEntity restoredGott = ids.read(gott.getId());

        assertEquals(restoredMalte.getName(),"Malte");
        assertEquals(restoredMalte.getPowerlevel(), 9001);
        assertEquals(restoredGott.getName(),"Gott");
        assertEquals(restoredGott.getPowerlevel(),2654);
    }

    @Test
    public void testPutUpdateAndRead()
    {
        Reservoir<String,TestReservoirEntity> ids = new Reservoir<>(10, new NirvanaQueueSleeper<>());

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);

        TestReservoirEntity updateMalte = new TestReservoirEntity("Next-level Malte", 17001);

        ids.put(malte);
        ids.put(gott);

        updateMalte.setId(malte.getId());
        ids.put(updateMalte);

        TestReservoirEntity restoredMalte = ids.read(malte.getId());
        TestReservoirEntity restoredGott = ids.read(gott.getId());

        assertEquals(restoredMalte.getName(),"Next-level Malte");
        assertEquals(restoredMalte.getPowerlevel(), 17001);
        assertEquals(restoredGott.getName(),"Gott");
        assertEquals(restoredGott.getPowerlevel(),2654);
    }

    @Test
    public void testPutAndDelete()
    {

    }*/
}
