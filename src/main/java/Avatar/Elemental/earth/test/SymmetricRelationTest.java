package Avatar.Elemental.earth.test;

import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.earth.SymmetricRelation;
import Avatar.Test.TestReservoirEntity;
import org.junit.Test;

import static org.junit.Assert.*;

public class SymmetricRelationTest
{
    @Test
    public void testPutAndGetAreSymmetric()
    {
        SymmetricRelation<TestReservoirEntity> symmetricRelation = new SymmetricRelation<>();

        TestReservoirEntity one = new TestReservoirEntity("one",1);
        TestReservoirEntity two = new TestReservoirEntity("two", 2);
        TestReservoirEntity three = new TestReservoirEntity("three", 3);

        symmetricRelation.put(one, two);
        symmetricRelation.put(one, three);
        symmetricRelation.put(three,two);

        ReservoirEntityList<TestReservoirEntity> list = symmetricRelation.get(one);
        assertFalse(list.contains(one));
        assertTrue(list.contains(two));
        assertTrue(list.contains(three));

        list = symmetricRelation.get(three);
        assertTrue(list.contains(one));
        assertTrue(list.contains(two));
        assertFalse(list.contains(three));
    }
}
