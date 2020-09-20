package examples.toxitter;

import Toxitter.Core.*;
import Toxitter.Core.remake.Friends;

public class ToxitterStandardConfiguratedEnvironment
{
    public static void seed()
    {
        Seeder.seedServeStandardModels();
        Seeder.seedAdmin("adminpwd");
        Seeder.seedStandardTestUsers();
        Seeder.seedSamplePostsWithReactions();
    }

    public static void serveStandardModels()
    {
        ToxitterClassRouter.serve(Post.class);
        ToxitterClassRouter.serve(Feed.class);
        ToxitterClassRouter.serve(UserReservoir.class);
        ToxitterClassRouter.serve(ToxitterSecurity.class);
        ToxitterClassRouter.serve(Notifications.class);
        ToxitterClassRouter.serve(User.class);
        ToxitterClassRouter.serve(Friends.class);
    }

    public static void up() throws Exception
    {
        seed();
        serveStandardModels();
        ToxitterServer.up(8001,10);
    }
}