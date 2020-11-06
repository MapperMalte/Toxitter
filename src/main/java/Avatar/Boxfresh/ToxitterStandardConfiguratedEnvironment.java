package Avatar.Boxfresh;

import Avatar.Boxfresh.routes.LoginAndRegister;
import Avatar.Boxfresh.routes.User;
import Avatar.Boxfresh.reservoirs.UserReservoir;
import Avatar.Boxfresh.routes.Chat;
import Avatar.Boxfresh.routes.Friend;
import Avatar.Elemental.aether.ToxitterClassRouter;
import Avatar.Elemental.aether.BookOfGorlon;

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
        ToxitterClassRouter.serve(Friend.class);
        ToxitterClassRouter.serve(Chat.class);
    }

    public static void up() throws Exception
    {
        seed();
        serveStandardModels();
        BookOfGorlon.up(8001,10);
    }
}