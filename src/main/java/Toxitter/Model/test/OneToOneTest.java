package Toxitter.Model.test;

import Toxitter.Model.ID;
import Toxitter.Model.OneToOne;
import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.annotations.Table;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OneToOneTest
{
    @Table(primaryKey = "id",tableName = "testreservoir")
    private static class TestReservoirEntity extends ReservoirEntity<String>
    {
        private String id;
        private String name;
        private int powerlevel;

        public TestReservoirEntity(String name, int powerlevel)
        {
            this.id = ID.makeId();
            this.name = name;
            this.powerlevel = powerlevel;
        }

        @Override
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getPowerlevel() { return powerlevel; }
        public void setPowerlevel(int powerlevel) { this.powerlevel = powerlevel; }
    }

    @Test
    public void testPutAndRead()
    {
        OneToOne<String,TestReservoirEntity> ids = new OneToOne<>(10);

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);

        ids.put(malte.getId(),malte);
        ids.put(gott.getId(),gott);

        TestReservoirEntity restoredMalte = ids.read(malte.getId());
        TestReservoirEntity restoredGott = ids.read(gott.getId());

        assertEquals(restoredMalte.name,"Malte");
        assertEquals(restoredMalte.powerlevel, 9001);
        assertEquals(restoredGott.name,"Gott");
        assertEquals(restoredGott.powerlevel,2654);
    }

    @Test
    public void testPutUpdateAndRead()
    {
        OneToOne<String,TestReservoirEntity> ids = new OneToOne<>(10);

        TestReservoirEntity malte = new TestReservoirEntity("Malte",9001);
        TestReservoirEntity gott = new TestReservoirEntity("Gott",2654);

        TestReservoirEntity updateMalte = new TestReservoirEntity("Next-level Malte", 17001);

        ids.put(malte.getId(),malte);
        ids.put(gott.getId(),gott);

        ids.put(malte.getId(),updateMalte);

        TestReservoirEntity restoredMalte = ids.read(malte.getId());
        TestReservoirEntity restoredGott = ids.read(gott.getId());

        assertEquals(restoredMalte.name,"Next-level Malte");
        assertEquals(restoredMalte.powerlevel, 17001);
        assertEquals(restoredGott.name,"Gott");
        assertEquals(restoredGott.powerlevel,2654);
    }

    @Test
    public void testPutAndDelete()
    {

    }
}
