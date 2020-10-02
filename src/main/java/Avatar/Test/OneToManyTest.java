package Avatar.Test;

import static org.junit.Assert.assertEquals;

public class OneToManyTest
{
    /*
    @Test
    public void testInsertMultipleAndRead()
    {
        OneToMany<String,String,TestReservoirEntity> ids =
            new OneToMany<String,String,TestReservoirEntity>(10,
            new NirvanaQueueSleeper<String,ReservoirEntityList<String,TestReservoirEntity>>());

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);
        TestReservoirEntity luzifer = new TestReservoirEntity("Luzifer",666);

        ids.put(malte.getId(),malte);
        ids.put(malte.getId(),gott);

        DiamondList<TestReservoirEntity> restored = ids.get(malte.getId());

        assertEquals(restored.contains(malte),true);
        assertEquals(restored.contains(gott),true);
        assertEquals(restored.contains(luzifer),false);

        ids.put(malte.getId(),luzifer);
        assertEquals(restored.contains(luzifer),true);
    }

    @Test
    public void testGetgetAndPutput()
    {
        OneToMany<Integer,String,TestReservoirEntity> ids =
                new OneToMany<Integer,String,TestReservoirEntity>(10,
                        new NirvanaQueueSleeper<Integer,ReservoirEntityList<String,TestReservoirEntity>>());

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);
        TestReservoirEntity luzifer = new TestReservoirEntity("Luzifer",666);

        ids.put(1,malte);
        ids.put(1,gott);
        ids.put(2,luzifer);

        assertEquals(ids.getget(1,malte.getId()).getName(),"Malte");
        assertEquals(ids.getget(1,gott.getId()).getName(),"Gott");
        assertEquals(ids.getget(2,luzifer.getId()).getName(),"Luzifer");

        TestReservoirEntity peter = new TestReservoirEntity("Peter",40);

        ids.putput(1,gott.getId(),peter);
        assertEquals(ids.getget(1,gott.getId()).getName(),"Peter");

    }*/
}