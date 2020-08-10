package Toxitter.Model.factoryfresh;

import Toxitter.Model.Reservoir;
import Toxitter.Core.annotations.FetchAt;
import Toxitter.Security.annotations.Protected;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import com.google.gson.Gson;
import Toxitter.Logging.Ullog;

import java.util.TreeMap;

@FetchAt(route="user")
public class UserReservoir extends Reservoir
{
    private static TreeMap<String,User> usersByMail = new TreeMap<>();
    private static TreeMap<String,User> usersById = new TreeMap<>();

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
            @RequestParam(name = "name", obligatory = true) String name,
            @RequestParam(name = "email",obligatory = true) String email,
            @RequestParam(name = "pwd",obligatory = true) String pwd)
    {

        User newUser = new User();
        newUser.userId = makeId();
        newUser.name = name;
        newUser.email = email;
        newUser.pwd = pwd;
        usersByMail.put(email,newUser);
        usersById.put(newUser.userId,newUser);
        return newUser.userId;
    }

    @Route(route = "all")
    @Protected(scope = "admin")
    public static String all(@RequestParam(name="token",obligatory = true) String token)
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
            @RequestParam(name = "userId", obligatory = true) String userId,
            @RequestParam(name="token",obligatory = true) String token)
    {
        Gson gson = new Gson();
        User user = UserReservoir.getUserByUserId(userId);
        return gson.toJson(new PublicUserInfo(user.name,user.photoUrl));
    }
}