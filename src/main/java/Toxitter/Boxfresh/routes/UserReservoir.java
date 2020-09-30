package Toxitter.Boxfresh.routes;

import Toxitter.Elemental.earth.ID;
import Toxitter.Annotations.core.FetchAt;
import Toxitter.Security.UserPrivileges;
import Toxitter.Annotations.security.Protected;
import Toxitter.Annotations.core.Route;
import com.google.gson.Gson;
import Toxitter.Logging.Ullog;

import java.util.TreeMap;

@FetchAt(route="user")
public class UserReservoir
{
    private static TreeMap<String, User> usersByMail = new TreeMap<>();
    private static TreeMap<String,User> usersById = new TreeMap<>();
    private static TreeMap<String,User> usersByName = new TreeMap<>();

    /**
     *
     * @param email
     * @return
     */
    public static String getUserIdByMail(String email)
    {
        return  usersByMail.get(email).userId;
    }

    /**
     *
     * @param id
     * @return
     */
    public static User getUserByUserId(String id)
    {
        return  usersById.get(id);
    }

    public static User getUserByUserName(String name)
    {
        return  usersByName.get(name);
    }

    /**
     *
     * @param email
     * @return
     */
    public static User getUserByMail(String email)
    {
        return  usersByMail.get(email);
    }

    public static boolean userExists(String email)
    {
        return usersByMail.containsKey(email);
    }

    /**
     *
     * @param name
     * @param email
     * @param pwd
     * @return
     */
    public static String registerUser(
            String name,
            String email,
            String pwd)
    {

        User newUser = new User();
        newUser.userId = ID.makeId();
        newUser.name = name;
        newUser.email = email;
        newUser.pwd = pwd;
        usersByMail.put(email,newUser);
        usersById.put(newUser.userId,newUser);
        usersByName.put(newUser.name,newUser);
        UserPrivileges.add(newUser.userId,"user");
        return newUser.userId;
    }

    @Route(route = "all")
    @Protected(scope = "admin")
    public static String all(String token)
    {
        Gson gson = new Gson();
        Ullog.put(UserReservoir.class,"Route /all called.");
        return gson.toJson(usersById);
    }

    public static class PublicUserInfo
    {
        String displayName = "";
        String photoUrl = "";
        public PublicUserInfo(String displayName, String photoUrl)
        {
            this.displayName = displayName;
            this.photoUrl = photoUrl;
        }
    }

    @Route(route = "info")
    @Protected(scope = "user")
    public static String single(
            String userId,
            String token)
    {
        Gson gson = new Gson();
        User user = UserReservoir.getUserByUserId(userId);
        return gson.toJson(new PublicUserInfo(user.name,user.photoUrl));
    }
}