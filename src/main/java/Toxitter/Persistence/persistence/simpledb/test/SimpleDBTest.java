package Toxitter.Persistence.persistence.simpledb.test;

import Toxitter.Model.test.TestReservoirEntity;
import Toxitter.Persistence.DataAccessToReservoirEntity;
import Toxitter.Persistence.ReservoirEntityDataPresenter;
import Toxitter.Persistence.persistence.simpledb.SimpleDB;
import org.junit.Test;

public class SimpleDBTest
{
    @Test
    public void testWrite100()
    {
        SimpleDB db = new SimpleDB();

        TestReservoirEntity tre = new TestReservoirEntity();
        System.out.println(tre.getTable().tableName());
        db.openConnection(tre.getTable().tableName());

        for(int i = 0; i < 100; i++)
        {
            db.put(new TestReservoirEntity());
        }

        db.flushAndClose();
        System.out.println("NOW READ");
        db.read(tre.getTable().tableName());
    }
}
