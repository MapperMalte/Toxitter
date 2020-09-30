package Toxitter.Connection;

import Toxitter.Logging.Ullog;
import Toxitter.Security.ToxitterSecurityMiddleware;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public abstract class Middleware
{
    /**
     *
     * @param uri
     * @param param
     * @return data[0] the new URI without the Parameter, data[1] the Parameter Value
     */
    public static String extractGetParam(String uri, String param)
    {
        int beginTokenParamIndex = uri.indexOf(param);
        if ( beginTokenParamIndex == -1 )
        {
            return "";
        }
        int beginTokenIndex
                = uri.indexOf("?"+ToxitterSecurityMiddleware.TOKEN_IDENTIFIER+"=")
                +ToxitterSecurityMiddleware.TOKEN_IDENTIFIER.length()+2;

        beginTokenIndex = Math.max(beginTokenIndex,0);
        System.out.println("To beginToken: "+uri.substring(0,beginTokenIndex));
        System.out.println("From there on: "+uri.substring(beginTokenIndex,uri.length()));
        int endTokenIndex = uri.indexOf("?",beginTokenIndex);
        if ( endTokenIndex == -1 )
        {
            endTokenIndex = uri.length();
        }
        System.out.println("Startokenindex: "+beginTokenIndex);
        System.out.println("Endtokenindex: "+endTokenIndex);
        String token = uri.substring(beginTokenIndex,endTokenIndex);
        if (Ullog.active) Ullog.put(ToxitterSecurityMiddleware.class,"Extracted token "+token+" from Uri: "+uri);
        uri = uri.substring(0,beginTokenParamIndex)+uri.substring(endTokenIndex,uri.length());
        if (Ullog.active) Ullog.put(ToxitterSecurityMiddleware.class,"Now uri: "+uri);
        return token;
    }

    public static String extractPostParam(JsonObject postData, String param)
    {
        for (Map.Entry<String, JsonElement> entry : postData.entrySet())
        {
            if ( entry.getKey().equals(param) )
            {
                postData.remove(entry.getKey());
                return entry.getValue().toString();
            }
        }
        return "";
    }
}
