package Avatar.Elemental.water.test;

import Avatar.Boxfresh.routes.User;
import Avatar.Boxfresh.routes.UserReservoir;
import Avatar.Elemental.water.Pool;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PoolTest
{
    @Test
    public void test()
    {
        Pool<TestOutput> pool = new Pool();

        User malte = UserReservoir.registerUser("malte","malte_nolden@yahoo.de","pwd");
        pool.subscribe(malte);

        TestOutput hallo = new TestOutput("HALLO");

        pool.push(hallo);
        pool.push(new TestOutput("WELT"));
        assertTrue(pool.get(malte).contains(hallo));
        pool.setPusher(new TestPusher());

        pool.userComesOnline(malte);
    }
}
