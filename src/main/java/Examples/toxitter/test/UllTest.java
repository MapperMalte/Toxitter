package Examples.toxitter.test;

import Avatar.Boxfresh.routes.User;
import Avatar.Boxfresh.reservoirs.UserReservoir;
import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.earth.ID;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UllTest
{
    @Test
    public void testUserReservoir()
    {
        BookOfIlaan.write(UserReservoir.registerUser("Malte Nolden","malte_nolden@yahoo.de", User.getPwdHashMock()).userId);
        BookOfIlaan.write(UserReservoir.registerUser("Niklas Köhler","nkoehler@gmx.de", User.getPwdHashMock()).userId);
        BookOfIlaan.write(UserReservoir.getUserIdByMail("malte_nolden@yahoo.de"));

        assertEquals(UserReservoir.getUserByUserId(UserReservoir.getUserIdByMail("malte_nolden@yahoo.de")).getName(),"Malte Nolden");
    }

    @Test
    public void testLikes()
    {
        UserReservoir.registerUser("Malte Nolden","malte_nolden@yahoo.de", User.getPwdHashMock());
        UserReservoir.registerUser("Niklas Köhler","nkoehler@gmx.de", User.getPwdHashMock());

        ID malteId = UserReservoir.getUserByMail("malte_nolden@yahoo.de").getId();
        ID niklasId = UserReservoir.getUserByMail("nkoehler@gmx.de").getId();

        BookOfIlaan.write("Maltes ID: "+malteId.toString());
        BookOfIlaan.write("Niklas ID: "+niklasId.toString());
    }
}