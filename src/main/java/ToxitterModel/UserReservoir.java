package ToxitterModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.locality.toxitterbackend2.Ullog;
import org.apache.commons.text.StringEscapeUtils;

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
    public static String all(@RequestParam(name="token",obligatory = false) String token)
    {
        Gson gson = new Gson();
        Ullog.put(UserReservoir.class,"Route /all called.");
        return gson.toJson(usersById);
    }
}
