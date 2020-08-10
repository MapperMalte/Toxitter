package Toxitter.Security.test;

import Toxitter.Model.factoryfresh.UserReservoir;
import Toxitter.Security.ToxitterSecurity;
import Toxitter.Security.UserPrivileges;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToxitterSecurityTest
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

        ToxitterSecurity.addRequiredScopeToRoute("admin","/user/all");

        String token = ToxitterSecurity.getTokenForScope(userId,"drölf","admin");
        String userAccessToken = ToxitterSecurity.getTokenForScope(unprivilegedUserId,"vierzehn","user");
        String adminAccessRefreshToken = ToxitterSecurity.exchangeForAccessToken(token);


        System.out.println("Token: "+token);
        System.out.println("AccessToken: "+adminAccessRefreshToken);
        System.out.println("Unprivileged AccessToken: "+userAccessToken);
        assertEquals(ToxitterSecurity.hasAccesToRoute(adminAccessRefreshToken,"/user/all"),true);
        assertEquals(ToxitterSecurity.hasAccesToRoute(userAccessToken,"/user/all"),false);
        assertEquals(ToxitterSecurity.hasAccesToRoute(adminAccessRefreshToken,"/everyone"),true);
        assertEquals(ToxitterSecurity.hasAccesToRoute(userAccessToken,"/everyone"),true);
    }

    @Test
    public void testOnlyValidTokenWithCorrectCredentials()
    {
        String userId = UserReservoir.registerUser("Malte","mail","drölf");
        UserPrivileges.add(userId,"user");

        String noToken = ToxitterSecurity.getTokenForScope(userId,"dröf", "user");
        String token = ToxitterSecurity.getTokenForScope(userId,"drölf", "user");

        assertEquals(ToxitterSecurity.isValidToken(noToken),false);
        assertEquals(ToxitterSecurity.isValidToken(token),true);

    }
}
