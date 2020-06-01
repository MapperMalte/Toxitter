package Toxitter.Security.concepts;

import Toxitter.Logging.Ullog;
import Toxitter.Security.ToxitterSecurityMiddleware;
import com.sun.jndi.toolkit.url.Uri;

import java.util.TreeMap;

public abstract class Middleware
{
    /**
     *
     * @param uri
     * @param param
     * @return data[0] the new URI without the Parameter, data[1] the Parameter Value
     */
    public static String[] extractParam(String uri, String param)
    {
        String[] data = new String[2];
        int beginTokenParamIndex = uri.indexOf(param);
        if ( beginTokenParamIndex == -1 )
        {
            return null;
        }
        int beginTokenIndex = uri.indexOf("=",beginTokenParamIndex)+1;
        int endTokenIndex = Math.max(uri.indexOf("?",beginTokenIndex),uri.length());
        String token = uri.substring(beginTokenIndex,endTokenIndex);
        if (Ullog.active) Ullog.put(ToxitterSecurityMiddleware.class,"Extracted token "+token+" from Uri: "+uri);
        uri = uri.substring(0,beginTokenParamIndex)+uri.substring(endTokenIndex,uri.length());
        if (Ullog.active) Ullog.put(ToxitterSecurityMiddleware.class,"Now uri: "+uri);
        data[0] = uri;
        data[1] = token;
        return data;
    }
}
