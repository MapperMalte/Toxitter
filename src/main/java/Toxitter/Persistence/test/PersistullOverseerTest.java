package Toxitter.Persistence.test;

import Toxitter.Model.factoryfresh.User;
import Toxitter.Persistence.PersistingOctopus;
import org.junit.Test;

public class PersistullOverseerTest
{
    @Test
    public void testPersistull()
    {
        PersistingOctopus.serve(User.class);
    }
}
