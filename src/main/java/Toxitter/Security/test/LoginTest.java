package Toxitter.Security.test;

import Toxitter.Core.UserReservoir;
import Toxitter.Core.Login;
import Toxitter.Security.UserPrivileges;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginTest
{
    @Test
    public void testTokenHub()
    {
        System.out.println("---- SecruityTest");
        System.out.println("testTokenHub");
        String userId = UserReservoir.registerUser("Malte","mail","drölf");
        String unprivilegedUserId = UserReservoir.registerUser("Peter","mail2","vierzehn");
        UserPrivileges.add(userId,"admin");
        UserPrivileges.add(unprivilegedUserId,"user");

        Login.addRequiredScopeToRoute("admin","/user/all");

        String token = Login.getTokenForScope(userId,"drölf","admin");
        String userAccessToken = Login.getTokenForScope(unprivilegedUserId,"vierzehn","user");
        String adminAccessRefreshToken = Login.exchangeForAccessToken(token);


        System.out.println("Token: "+token);
        System.out.println("AccessToken: "+adminAccessRefreshToken);
        System.out.println("Unprivileged AccessToken: "+userAccessToken);
        assertEquals(Login.hasAccesToRoute(adminAccessRefreshToken,"/user/all"),true);
        assertEquals(Login.hasAccesToRoute(userAccessToken,"/user/all"),false);
        assertEquals(Login.hasAccesToRoute(adminAccessRefreshToken,"/everyone"),true);
        assertEquals(Login.hasAccesToRoute(userAccessToken,"/everyone"),true);
    }

    @Test
    public void testOnlyValidTokenWithCorrectCredentials()
    {
        String userId = UserReservoir.registerUser("Malte","mail","drölf");
        UserPrivileges.add(userId,"user");

        String noToken = Login.getTokenForScope(userId,"dröf", "user");
        String token = Login.getTokenForScope(userId,"drölf", "user");

        assertEquals(Login.isValidToken(noToken),false);
        assertEquals(Login.isValidToken(token),true);

    }
}
