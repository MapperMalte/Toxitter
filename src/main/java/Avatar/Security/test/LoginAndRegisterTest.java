package Avatar.Security.test;

import Avatar.Boxfresh.reservoirs.UserReservoir;
import Avatar.Boxfresh.routes.LoginAndRegister;
import Avatar.Security.UserPrivileges;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginAndRegisterTest
{
    @Test
    public void testTokenHub()
    {
        System.out.println("---- SecurityTest");
        System.out.println("testTokenHub");
        String userId = UserReservoir.registerUser("Malte","mail","drölf").userId;
        String unprivilegedUserId = UserReservoir.registerUser("Peter","mail2","vierzehn").userId;
        UserPrivileges.add(userId,"admin");
        UserPrivileges.add(unprivilegedUserId,"user");

        LoginAndRegister.addRequiredScopeToRoute("admin","/user/all");

        String token = LoginAndRegister.getTokenForScope(userId,"drölf","admin");
        String userAccessToken = LoginAndRegister.getTokenForScope(unprivilegedUserId,"vierzehn","user");
        String adminAccessRefreshToken = LoginAndRegister.exchangeForAccessToken(token);


        System.out.println("Token: "+token);
        System.out.println("AccessToken: "+adminAccessRefreshToken);
        System.out.println("Unprivileged AccessToken: "+userAccessToken);
        assertEquals(LoginAndRegister.hasAccesToRoute(adminAccessRefreshToken,"/user/all"),true);
        assertEquals(LoginAndRegister.hasAccesToRoute(userAccessToken,"/user/all"),false);
        assertEquals(LoginAndRegister.hasAccesToRoute(adminAccessRefreshToken,"/everyone"),true);
        assertEquals(LoginAndRegister.hasAccesToRoute(userAccessToken,"/everyone"),true);
    }

    @Test
    public void testOnlyValidTokenWithCorrectCredentials()
    {
        String userId = UserReservoir.registerUser("Malte","mail","drölf").userId;
        UserPrivileges.add(userId,"user");

        String noToken = LoginAndRegister.getTokenForScope(userId,"dröf", "user");
        String token = LoginAndRegister.getTokenForScope(userId,"drölf", "user");

        assertEquals(LoginAndRegister.isValidToken(noToken),false);
        assertEquals(LoginAndRegister.isValidToken(token),true);

    }
}
