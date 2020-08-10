package Toxitter.Security;

import Toxitter.Core.annotations.FetchAt;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import Toxitter.Model.Reservoir;
import Toxitter.Model.factoryfresh.User;
import Toxitter.Model.factoryfresh.UserReservoir;

import java.util.TreeMap;

@FetchAt(route = "auth")
public class ToxitterSecurity extends Reservoir
{
    private static TreeMap<String,AccessToken> tokens = new TreeMap<>();
    private static TreeMap<String,AccessToken> refreshTokens = new TreeMap<>();
    private static TreeMap<String,String> routeRequiredScope = new TreeMap<>();

    public static class AccessToken
    {
        String scope;
        String token;
        public AccessToken(String scope, String token)
        {
            this.scope = scope;
            this.token = token;
        }
    }

    /**
     *
     * @param scope
     * @param route
     */
    public static void addRequiredScopeToRoute(String scope, String route)
    {
        routeRequiredScope.put(route,scope);
    }

    /**
     *
     * @param userId
     * @param password
     * @param scope
     * @return
     */
    @Route(route = "token")
    public static String getTokenForScope(
            @RequestParam(name = "userId", obligatory = true) String userId,
            @RequestParam(name="password",obligatory = true) String password,
            @RequestParam(name = "scope",obligatory = true) String scope)
    {
        User user = UserReservoir.getUserByUserId(userId);
        if ( user == null )
        {
            return "User with ID "+user+" does not exist.";
        }
        if ( !UserPrivileges.hasUserPrivilege(userId,scope) )
        {
            return "User "+user.getName()+" does not have "+scope+" privileges!";
        }
        if ( user.pwdCorrect(password) )
        {
            String id = makeId();
            tokens.put(id,new AccessToken(scope,id));
            return id;
        } else {
            return "Incorrect Password!";
        }
    }

    /**
     *
     * @param token
     * @return
     */
    @Route(route = "tokenexchange")
    public static String exchangeForAccessToken(@RequestParam(name = "token", obligatory = true) String token)
    {
        AccessToken old = toToken(token);
        if ( !(old==null) )
        {
            AccessToken accessToken = new AccessToken(old.scope,makeId());
            refreshTokens.put(accessToken.token, accessToken);
            return accessToken.token;
        } else {
            return "Not a valid token: "+token;
        }
    }

    @Route(route = "login")
    public static String attemptLogin(
            @RequestParam(name = "email", obligatory = true) String email,
            @RequestParam(name="password", obligatory = true) String password)
    {
        if ( !UserReservoir.userExists(email) )
        {
            return "User with email "+email+" does not exist!";
        }
        User user = UserReservoir.getUserByMail(email);
        String userId = UserReservoir.getUserIdByMail(email);
        return "{\"userId\": \""+userId+"\", "
                +"\"accessToken\": \""+
                getTokenForScope(userId,password,"user")+"\", "
                +"\"userId\": \""+
                user.getId()+"\", "
                +"\"userName\": \""+
                user.getName()+"\", "
                +"\"photoUrl\": \""+
                user.photoUrl
                +"\"}";
    }

    @Route(route="register")
    public static String registerNewUser(
            @RequestParam(name = "surname", obligatory = true) String surname,
            @RequestParam(name = "name", obligatory = true) String name,
            @RequestParam(name = "email", obligatory = true) String email,
            @RequestParam(name="password", obligatory = true) String password
    )
    {
        if ( UserReservoir.userExists(email) )
        {
            return "User with email "+email+" already exists!";
        }
        String newUserId = UserReservoir.registerUser((surname+" "+name),email,password);
        UserPrivileges.add(newUserId,"user");
        User user = UserReservoir.getUserByMail(email);

        return "{\"userId\": \""+newUserId+"\", "
                +"\"accessToken\": \""+
                getTokenForScope(newUserId,password,"user")+"\", "
                +"\"userId\": \""+
                user.getId()+"\", "
                +"\"userName\": \""+
                user.getName()+"\", "
                +"\"photoUrl\": \""+
                user.photoUrl
                +"\"}";
    }

    /**
     *
     * @param token
     * @return
     */
    public static boolean isValidToken(String token)
    {
        return !(toToken(token)==null);
    }

    /**
     *
     * @param token
     * @return
     */
    private static AccessToken toToken(String token)
    {
        AccessToken accessToken = null;

        if ( tokens.containsKey(token) )
        {
            accessToken = tokens.get(token);
        } else if ( refreshTokens.containsKey(token) )
        {
            accessToken = refreshTokens.get(token);
        }

        return accessToken;
    }

    /**
     *
     * @param token
     * @param route
     * @return
     */
    public static boolean hasAccesToRoute(String token, String route)
    {
        System.out.println("CheckAccess");
        if ( routeRequiredScope.containsKey(route) )
        {
            AccessToken accessToken = toToken(token);
            if ( accessToken == null )
                return false;
            return routeRequiredScope.get(route).equals(accessToken.scope);
        } else {
            return true;
        }
    }

    public static boolean routeRequiresAnyPrivilege(String route)
    {
        return routeRequiredScope.containsKey(route);
    }
}