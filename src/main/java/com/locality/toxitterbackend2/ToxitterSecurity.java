package com.locality.toxitterbackend2;

import ToxitterModel.*;
import theory.DiamondList;

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
        String userId = UserReservoir.getUserIdByMail(email);
        return "{\"userId\": \""+userId+"\", "
                +"\"accessToken\": \""+
                getTokenForScope(userId,password,"user")
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
        return "{\"userId\": \""+newUserId+"\", "
                +"\"accessToken\": \""+
                getTokenForScope(UserReservoir.getUserIdByMail(email),password,"user")
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
        if ( routeRequiredScope.containsKey(route) )
        {
            return routeRequiredScope.get(route).equals(toToken(token).scope);
        } else {
            return true;
        }
    }

    public static boolean routeRequiresAnyPrivilege(String route)
    {
        return routeRequiredScope.containsKey(route);
    }
}