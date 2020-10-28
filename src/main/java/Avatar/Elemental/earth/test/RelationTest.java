package Avatar.Elemental.earth.test;

import Avatar.Elemental.earth.Relation;
import Avatar.Test.TestReservoirEntity;
import org.junit.Test;

import static org.junit.Assert.*;

public class RelationTest
{
    @Test
    public void testPutAndGet()
    {
        Relation<TestReservoirEntity, TestReservoirEntity> reservoirEntityRelation = new Relation<>();

        TestReservoirEntity one = new TestReservoirEntity("one",1);
        TestReservoirEntity two = new TestReservoirEntity("two", 2);
        TestReservoirEntity three = new TestReservoirEntity("three", 3);

        reservoirEntityRelation.put(one,two);
        reservoirEntityRelation.put(one, three);

        assertFalse(reservoirEntityRelation.forwardGet(one).contains(one));
        assertTrue(reservoirEntityRelation.forwardGet(one).contains(two));
        assertTrue(reservoirEntityRelation.forwardGet(one).contains(three));
        assertTrue(reservoirEntityRelation.backwardGet(two).contains(one));
        assertTrue(reservoirEntityRelation.backwardGet(three).contains(one));

        // Two and three have no forward-data stored to them. They should return null lists
        assertNull(reservoirEntityRelation.forwardGet(two));
        assertNull(reservoirEntityRelation.forwardGet(three));

        assertTrue(reservoirEntityRelation.hasLink(one, two));
        assertTrue(reservoirEntityRelation.hasLink(one, three));
        assertFalse(reservoirEntityRelation.hasLink(two, three));
    }
}
