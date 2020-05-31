package com.locality.toxitterbackend2;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ToxitterSecurityMiddleware
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
    public static boolean allowed(String route, String uri) throws IOException {
        if (ToxitterSecurity.routeRequiresAnyPrivilege(route) )
        {
            int beginTokenParamIndex = uri.indexOf(TOKEN_IDENTIFIER);
            if ( beginTokenParamIndex == -1 )
            {
                Ullog.put(ToxitterSecurityMiddleware.class,"Route "+route+" requires privilege, but no token provided in request!");
                return false;
            }
            int beginTokenIndex = uri.indexOf("=",beginTokenParamIndex)+1;
            int endTokenIndex = Math.max(uri.indexOf("?",beginTokenIndex),uri.length());
            String token = uri.substring(beginTokenIndex,endTokenIndex);
            Ullog.put(ToxitterSecurityMiddleware.class,"Extracted token "+token+" from Uri: "+uri);
            uri = uri.substring(0,beginTokenParamIndex)+uri.substring(endTokenIndex,uri.length());
            Ullog.put(ToxitterSecurityMiddleware.class,"Now uri: "+uri);

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
    }
}
