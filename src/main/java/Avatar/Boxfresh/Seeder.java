package Avatar.Boxfresh;

import Avatar.Boxfresh.routes.User;
import Avatar.Boxfresh.routes.UserReservoir;
import Avatar.Security.UserPrivileges;

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
}