package Toxitter.Core;

import Toxitter.Core.annotations.FetchAt;
import Toxitter.Security.annotations.Protected;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;
import Toxitter.Security.ToxitterSecurity;
import Toxitter.Logging.Ullog;
import example.ToxitterServer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ToxitterClassRouter
{
    public static void serve(Class c)
    {
        System.out.println("Serve class "+c.getName());
        if ( c.isAnnotationPresent(FetchAt.class) )
        {
            Ullog.put(ToxitterClassRouter.class,"Found FetchAt Annotation");
            String persist = "";
            FetchAt p = (FetchAt) c.getAnnotation(FetchAt.class);
            Method[] ms = c.getMethods();
            for(Method method: ms)
            {
                if (method.isAnnotationPresent(Route.class) )
                {
                    Ullog.put(ToxitterClassRouter.class, "Found Route annotation!");
                    Route r = (Route) method.getAnnotation(Route.class);
                    String allRoute = "/"+p.route()+"/"+r.route();
                    if ( method.isAnnotationPresent(Protected.class) )
                    {
                        Ullog.put(ToxitterClassRouter.class,"Route is protected!");
                        Protected pr = (Protected) method.getAnnotation(Protected.class);
                        Ullog.put(ToxitterClassRouter.class,"Required scope: "+pr.scope());
                        Ullog.put(ToxitterClassRouter.class,"Adding to TokenHub!");
                        ToxitterSecurity.addRequiredScopeToRoute(pr.scope(),allRoute);
                    } else {
                        Ullog.put(ToxitterClassRouter.class,"Route is NOT protected.");
                    }
                    ToxitterModelSignature sign = new ToxitterModelSignature();
                    sign.toxiClass = c;
                    for (Parameter param: method.getParameters() )
                    {
                        System.out.println("ParamName: "+param.getName());
                        if ( param.isAnnotationPresent(RequestParam.class))
                        {
                            RequestParam prm = (RequestParam) param.getAnnotation(RequestParam.class);
                            System.out.println("RequestParam Name: "+prm.name());
                            sign.addMethod(method,param,prm.name(),prm.obligatory());
                        }
                    }
                    ToxitterServer.registerRoute(allRoute,sign);
                }

            }
        } else {
            Ullog.put(ToxitterClassRouter.class,"No annotation!");
        }
    }
}
