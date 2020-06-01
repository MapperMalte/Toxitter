package Toxitter.Core.factoryfresh;

import Toxitter.Logging.Ullog;
import Toxitter.Model.Post;
import Toxitter.Model.User;
import Toxitter.Model.UserReservoir;
import Toxitter.Security.UserPrivileges;

public class Seeder
{
    public static void seedServeStandardModels()
    {

    }

    public static void seedAdmin(String pwd)
    {
        String userId = UserReservoir.registerUser("Admin","admin",pwd);
        User user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/women/93.jpg";
        UserPrivileges.add(userId,"user");
        UserPrivileges.add(userId,"admin");
    }

    /**
     * Adds Dagobert
     */
    public static void seedStandardTestUsers()
    {
        String userId = UserReservoir.registerUser("TestUser","mail","pwd");
        User user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/men/50.jpg";
        UserPrivileges.add(userId,"user");

        userId = UserReservoir.registerUser("Dagobert Duck","Dagobert","geld");
        user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/men/50.jpg";
        UserPrivileges.add(userId,"user");

        userId = UserReservoir.registerUser("Malte Nolden","malte@gmx.de","maltemail");
        user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/men/59.jpg";
        UserPrivileges.add(userId,"user");

        userId = UserReservoir.registerUser("Angela","Angela","merkel");
        user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/women/22.jpg";
        UserPrivileges.add(userId,"user");

        userId = UserReservoir.registerUser("Der Gott-Pharao","gott@pharaoh.de","unbegrenztemacht");
        user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/men/12.jpg";
        UserPrivileges.add(userId,"user");
        UserPrivileges.add(userId,"admin");

        userId = UserReservoir.registerUser("Dieter Dülidum","ddülidum@gmx.de","ddülidüli");
        user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/women/4.jpg";
        UserPrivileges.add(userId,"user");

        userId = UserReservoir.registerUser("Niklas Köhler","nkoehler@gmx.de","sodomorrha");
        user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/women/4.jpg";
        UserPrivileges.add(userId,"user");

        userId = UserReservoir.registerUser("Donald Trump","trump@hell.covfefe","covfefe");
        user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/women/4.jpg";
        UserPrivileges.add(userId,"user");


        userId = UserReservoir.registerUser("Mister X","mrx@gmx.de","HAHAHAHAHA");
        user = UserReservoir.getUserByUserId(userId);
        user.photoUrl = "https://randomuser.me/api/portraits/men/47.jpg";
    }

    public static void seedSamplePostsWithReactions()
    {
        String gottPharaohId = UserReservoir.getUserByMail("gott@pharaoh.de").getId();
        String malteId = UserReservoir.getUserByMail("malte@gmx.de").getId();
        String niklasId = UserReservoir.getUserByMail("nkoehler@gmx.de").getId();
        String trumpId = UserReservoir.getUserByMail("trump@hell.covfefe").getId();

        Post p1 = Post.create (
                gottPharaohId,
                "Liebe Gemeinde",
                "Der Gott-Pharaoh wünscht euch allen schöne Ostern. \n" +
                        "Mögen tausend Kristalle sich in eurem Leben zu Rosen entfalten. \n" +
                        "Wir werden eine neue Ära des Friedens und des Wohlstands einleiten.\n "
        );
        p1.react(trumpId, "heart");
        p1.react(malteId, "satisfied");
        p1.react(niklasId, "rage");

        Post p2 = Post.create(
                niklasId,
                "DOOMED",
                "Das ist megamäßig doomed dumm. Das Überunternehmen des Grauens hat gesprochen, " +
                        "und an seiner Spitze steht unser geliebter Gott-Pharaoh. Wollen wir uns nicht endlich" +
                        " gegen unsere gewaltsamen Unterdrücker auflehnen? Es ist im Gewand, der Sicherheit, " +
                        "dass wir unsere Freiheiten aufgeben mussten. Dass wir nun mit Drohnengeld zahlen und" +
                        " digitale Halsbänder tragen. Dass sie mit Prothesen unseren Verstand gekapert haben! " +
                        "Es ist Zeit, das wir uns auflehnen!");
        p2.react(trumpId, "rage");

        Post p3 = Post.create(
                trumpId,
                "FAKE NEWS!",
                "FAKE NEWS! FEWK NEWS @Niklas.");
        p3.react(gottPharaohId, "heart");
        p3.react(malteId, "cry");
        p3.react(niklasId, "cry");

        Post p4 = Post.create(
                malteId,
                "Der Schatten des Abendgleidfüßlers",
                "Der Schatten des Abendgleidfüßlers \n" +
                        "Sang abends sein Abendlied für das\n "+
                        "MAN ICH BIN DER ZERBÜRSTER"
        );
        p4.react(niklasId, "flushed");
    }
}
