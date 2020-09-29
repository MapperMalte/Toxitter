package Toxitter.Boxfresh.routes;

import Toxitter.Annotations.core.FetchAt;
import Toxitter.Annotations.core.Route;
import Toxitter.Boxfresh.output.LoginSuccess;
import Toxitter.Model.elemental.matter.ID;
import Toxitter.Security.UserPrivileges;

import java.util.TreeMap;

@FetchAt(route = "auth")
public class LoginAndRegister
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
            token = token.replaceAll("\"","");
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
            String userId,
            String password,
            String scope)
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
            String id = ID.makeId();
            System.out.println("Put token with id "+id+" for scope "+scope);
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
    public static String exchangeForAccessToken(String token)
    {
        AccessToken old = toToken(token);
        if ( !(old==null) )
        {
            AccessToken accessToken = new AccessToken(old.scope,ID.makeId());
            refreshTokens.put(accessToken.token, accessToken);
            return accessToken.token;
        } else {
            return "Not a valid token: "+token;
        }
    }

    @Route(route = "login")
    public static LoginSuccess attemptLogin(
            String email,
            String password)
    {
        if ( !UserReservoir.userExists(email) )
        {
            LoginSuccess loginSuccess = new LoginSuccess();
            loginSuccess.errormsg = "User with email "+email+" does not exist!";
            return loginSuccess;
        }
        User user = UserReservoir.getUserByMail(email);
        String userId = UserReservoir.getUserIdByMail(email);
        //Online.connect(ToxitterWebsocketHandler.current,user);

        LoginSuccess loginSuccess = new LoginSuccess();
        loginSuccess.accessToken = getTokenForScope(userId,password,"user");
        loginSuccess.userId = user.getId().toString();
        loginSuccess.userNamer = user.getName();
        loginSuccess.photoUrl = user.photoUrl;

        return loginSuccess;
    }

    @Route(route="register")
    public static String registerNewUser(
            String surname,
            String name,
            String email,
            String password
    )
    {
        if ( UserReservoir.userExists(email) )
        {
            return "User with email "+email+" already exists!";
        }
        String newUserId = UserReservoir.registerUser((surname+" "+name),email,password);
        User user = UserReservoir.getUserByMail(email);

        return "{\"userId\": \""+newUserId+"\", "
                +"\"accessToken\": \""+ getTokenForScope(newUserId,password,"user")+"\", "
                +"\"userName\": \""+ user.getName()+"\", "
                +"\"photoUrl\": \""+ user.photoUrl
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
        System.out.println("Checking Access of token "+token+" to route "+route);
        token = token.replaceAll("\"","");
        if ( routeRequiredScope.containsKey(route) )
        {
            AccessToken accessToken = toToken(token);
            if ( accessToken == null )
            {
                System.out.println("No tokken associated");
                return false;
            }
            return routeRequiredScope.get(route).equals(accessToken.scope);
        } else {
            return true;
        }
    }

    public static String getRequiredPrivilege(String route)
    {
        return routeRequiredScope.get(route);
    }

    public static boolean routeRequiresAnyPrivilege(String route)
    {
        return routeRequiredScope.containsKey(route);
    }
}