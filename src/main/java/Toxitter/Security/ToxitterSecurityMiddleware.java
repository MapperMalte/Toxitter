package Toxitter.Security;

import Toxitter.Model.concepts.Middleware;

import java.io.IOException;

public class ToxitterSecurityMiddleware extends Middleware
{
    public static final String TOKEN_IDENTIFIER = "tokenId";
    private static final int TOKEN_IDENTIFIER_LENGTH = TOKEN_IDENTIFIER.length();

    /**
     * Side effect: Strips the tokenId Param out of the request.
     * @param route
     * @param uri
     * @return
     * @throws IOException
     */
    /*
    public static boolean allowedGet(String route, String uri) throws IOException {
        if (ToxitterSecurity.routeRequiresAnyPrivilege(route) )
        {
            int beginTokenParamIndex = uri.indexOf(TOKEN_IDENTIFIER);
            if ( beginTokenParamIndex == -1 )
            {
                Ullog.put(ToxitterSecurityMiddleware.class,"Route "+route+" requires privilege, but no token provided in request!");
                return false;
            }

            String[] data = extractGetParam(uri,TOKEN_IDENTIFIER);
            uri = data[0];
            String token = data[1];

            if ( ToxitterSecurity.hasAccesToRoute(token,route) )
            {
                Ullog.put(ToxitterSecurityMiddleware.class,"Token "+token+" has Access to Route "+route+"!");
                return true;
            } else {
                Ullog.put(ToxitterSecurityMiddleware.class,"Token "+token+" does not have Access to Route "+route+"!");
                return false;
            }
        }
        return true;
    }*/
}
