package examples.toxitter;

import Toxitter.Boxfresh.routes.LoginAndRegister;
import Toxitter.Boxfresh.routes.User;
import Toxitter.Boxfresh.routes.UserReservoir;
import Toxitter.Core.*;
import Toxitter.Boxfresh.routes.Chat;
import Toxitter.Boxfresh.routes.Friends;
import Toxitter.Core.ToxitterClassRouter;

public class ToxitterStandardConfiguratedEnvironment
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
        ToxitterServer.up(8001,10);
    }
}