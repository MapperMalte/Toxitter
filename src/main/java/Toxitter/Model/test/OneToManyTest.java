package Toxitter.Model.test;

import Toxitter.Model.NirvanaQueueSleeper;
import Toxitter.Model.OneToMany;
import Toxitter.Model.OneToOne;
import org.junit.Test;
import theory.DiamondList;

import static org.junit.Assert.assertEquals;

public class OneToManyTest
{
    @Test
    public void testInsertMultipleAndRead()
    {
        OneToMany<String,TestReservoirEntity> ids =
                new OneToMany<String,TestReservoirEntity>(10);

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);
        TestReservoirEntity luzifer = new TestReservoirEntity("Luzifer",666);

        ids.put(malte.getId(),malte);
        ids.put(malte.getId(),gott);

        DiamondList<TestReservoirEntity> restored = ids.read(malte.getId());

        assertEquals(restored.contains(malte),true);
        assertEquals(restored.contains(gott),true);
        assertEquals(restored.contains(luzifer),false);

        ids.put(malte.getId(),luzifer);
        assertEquals(restored.contains(luzifer),true);
    }
}