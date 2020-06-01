package Toxitter.Core.factoryfresh;

public class ToxitterStandardConfiguratedEnvironment
{
    public static void seed()
    {
        Seeder.seedServeStandardModels();
        Seeder.seedAdmin("adminpwd");
        Seeder.seedStandardTestUsers();
        Seeder.seedSamplePostsWithReactions();
    }

    public static void up()
    {
        seed();
    }
}