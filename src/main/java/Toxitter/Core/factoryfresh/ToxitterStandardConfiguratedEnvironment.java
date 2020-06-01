package Toxitter.Core.factoryfresh;

import Toxitter.Core.ToxitterOverseer;
import Toxitter.Core.ToxitterServer;
import Toxitter.Security.ToxitterSecurity;

import java.io.IOException;

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
        ToxitterOverseer.serve(Toxitter.Model.Post.class);
        ToxitterOverseer.serve(Toxitter.Model.Feed.class);
        ToxitterOverseer.serve(Toxitter.Model.UserReservoir.class);
        ToxitterOverseer.serve(ToxitterSecurity.class);
    }

    public static void up() throws Exception
    {
        seed();
        serveStandardModels();
        ToxitterServer.up(8001,10);
    }
}