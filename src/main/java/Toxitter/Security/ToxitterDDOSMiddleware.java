package Toxitter.Security;

import Toxitter.Security.concepts.Middleware;
import com.sun.jndi.toolkit.url.Uri;

public class ToxitterDDOSMiddleware extends Middleware
{
    public static boolean allowed(String ip)
    {
        return true;
    }
}