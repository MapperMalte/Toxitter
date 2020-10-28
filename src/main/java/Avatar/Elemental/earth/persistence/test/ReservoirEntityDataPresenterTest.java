package Avatar.Elemental.earth.persistence.test;

import Avatar.Elemental.earth.ID;
import Avatar.Test.TestReservoirEntity;
import Avatar.Elemental.water.DataAccessToReservoirEntity;
import Avatar.Elemental.water.ReservoirEntityDataPresenter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReservoirEntityDataPresenterTest
{
    @Test
    public void testDataRepresentationCorrect()
    {
        TestReservoirEntity test = new TestReservoirEntity("Malte",9034);
        test.setId(new ID("#id"));
        DataAccessToReservoirEntity dataAccess = test.getDataAccess();
        assertEquals(dataAccess.get("name"),"Malte");
        assertEquals(dataAccess.get("powerlevel"),9034);

        ReservoirEntityDataPresenter depp = new ReservoirEntityDataPresenter(dataAccess);
        assertEquals(depp.getTable().tableName(),"testreservoir");

    }
}
