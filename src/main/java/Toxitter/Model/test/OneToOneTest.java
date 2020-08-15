package Toxitter.Model.test;

import Toxitter.Boxfresh.NirvanaQueueSleeper;
import Toxitter.Model.ID;
import Toxitter.Model.OneToOne;
import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.annotations.Table;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OneToOneTest
{
    @Test
    public void testPutAndRead()
    {
        OneToOne<String,TestReservoirEntity> ids = new OneToOne<>(10, new NirvanaQueueSleeper<>());

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);

        ids.put(malte.getId(),malte);
        ids.put(gott.getId(),gott);

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
        OneToOne<String,TestReservoirEntity> ids = new OneToOne<>(10, new NirvanaQueueSleeper<>());

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);

        TestReservoirEntity updateMalte = new TestReservoirEntity("Next-level Malte", 17001);

        ids.put(malte.getId(),malte);
        ids.put(gott.getId(),gott);

        ids.put(malte.getId(),updateMalte);

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

    }
}
