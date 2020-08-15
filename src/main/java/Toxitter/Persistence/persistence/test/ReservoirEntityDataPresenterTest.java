package Toxitter.Persistence.persistence.test;

import Toxitter.Model.test.TestReservoirEntity;
import Toxitter.Persistence.DataAccessToReservoirEntity;
import Toxitter.Persistence.ReservoirEntityDataPresenter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReservoirEntityDataPresenterTest
{
    @Test
    public void testDataRepresentationCorrect()
    {
        TestReservoirEntity test = new TestReservoirEntity("Malte",9034);
        test.setId("#id");
        DataAccessToReservoirEntity dataAccess = test.getDataAccess();
        assertEquals(dataAccess.get("name"),"Malte");
        assertEquals(dataAccess.get("powerlevel"),9034);

        ReservoirEntityDataPresenter depp = new ReservoirEntityDataPresenter(dataAccess);
        assertEquals(depp.getTable().tableName(),"testreservoir");

    }
}