package Toxitter.Model.elemental.matter.persistence.simpledb.test;

import Toxitter.Model.test.TestReservoirEntity;
import Toxitter.Model.elemental.wind.DataAccessToReservoirEntity;
import Toxitter.Model.elemental.matter.persistence.simpledb.SimpleDB;
import org.junit.Test;
import Toxitter.Model.elemental.sky.DiamondList;

import static org.junit.Assert.assertEquals;

public class SimpleDBTest
{
    @Test
    public void testWrite100()
    {
        SimpleDB db = new SimpleDB();
        String[] names = new String[100];

        TestReservoirEntity tre = new TestReservoirEntity();
        System.out.println(tre.getTable().tableName());
        db.openConnection(tre.getTable().tableName());

        for(int i = 0; i < 100; i++)
        {
            TestReservoirEntity trex = new TestReservoirEntity();
            db.put(trex);
            names[0] = trex.getName();
        }

        db.flushAndClose();
        System.out.println("NOW READ");
        DiamondList<DataAccessToReservoirEntity> dl = db.read(tre.getTable().tableName());

        dl.bottom();
        for(int i = 0; i < 100; i++)
        {
            assertEquals(dl.getCurrent().get("name"),names[i]);
            dl.next();
        }
    }
}