package Avatar.Boxfresh;

import Avatar.Boxfresh.routes.LoginAndRegister;
import Avatar.Boxfresh.routes.User;
import Avatar.Boxfresh.routes.UserReservoir;
import Avatar.Boxfresh.routes.Chat;
import Avatar.Boxfresh.routes.Friends;
import Avatar.Elemental.aether.ToxitterClassRouter;
import Avatar.Elemental.aether.BookOfGorlon;
import Avatar.Elemental.earth.BookOfGrabbadur;

public class
ToxitterStandardConfiguratedEnvironment
{
    public static void seed()
    {
        Seeder.seedServeStandardModels();
        Seeder.seedAdmin("adminpwd");
        Seeder.seedStandardTestUsers();
    }

    public static void serveStandardModels()
    {
        ToxitterClassRouter.serve(UserReservoir.class);
        ToxitterClassRouter.serve(LoginAndRegister.class);
        ToxitterClassRouter.serve(User.class);
        ToxitterClassRouter.serve(Friends.class);
        ToxitterClassRouter.serve(Chat.class);
    }

    public static void up() throws Exception
    {
        seed();
        serveStandardModels();
        BookOfGorlon.up(8001,10);
    }
}