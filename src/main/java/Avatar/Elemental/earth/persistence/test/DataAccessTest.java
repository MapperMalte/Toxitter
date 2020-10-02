package Avatar.Elemental.earth.persistence.test;

import Avatar.Test.TestReservoirEntity;
import Avatar.Elemental.water.DataAccessToReservoirEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataAccessTest
{
    @Test
    public void test_DataAccessToReservoirEntity_AccessesFieldsCorrectly()
    {
        TestReservoirEntity test = new TestReservoirEntity("Malte",9034);
        test.setId("#id");
        DataAccessToReservoirEntity dataAccess = test.getDataAccess();
        assertEquals(dataAccess.get("name"),"Malte");
        assertEquals(dataAccess.get("powerlevel"),9034);
    }
}
