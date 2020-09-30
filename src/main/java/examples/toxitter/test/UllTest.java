package examples.toxitter.test;

import Toxitter.Boxfresh.routes.User;
import Toxitter.Boxfresh.routes.UserReservoir;
import Toxitter.Logging.Ullog;
import Toxitter.Core.Elemental.earth.ID;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UllTest
{
    @Test
    public void testUserReservoir()
    {
        Ullog.put(UserReservoir.registerUser("Malte Nolden","malte_nolden@yahoo.de", User.getPwdHashMock()));
        Ullog.put(UserReservoir.registerUser("Niklas Köhler","nkoehler@gmx.de", User.getPwdHashMock()));
        Ullog.put(UserReservoir.getUserIdByMail("malte_nolden@yahoo.de"));

        assertEquals(UserReservoir.getUserByUserId(UserReservoir.getUserIdByMail("malte_nolden@yahoo.de")).getName(),"Malte Nolden");
    }

    @Test
    public void testLikes()
    {
        UserReservoir.registerUser("Malte Nolden","malte_nolden@yahoo.de", User.getPwdHashMock());
        UserReservoir.registerUser("Niklas Köhler","nkoehler@gmx.de", User.getPwdHashMock());

        ID malteId = UserReservoir.getUserByMail("malte_nolden@yahoo.de").getId();
        ID niklasId = UserReservoir.getUserByMail("nkoehler@gmx.de").getId();

        Ullog.put("Maltes ID: "+malteId.toString());
        Ullog.put("Niklas ID: "+niklasId.toString());
    }
}