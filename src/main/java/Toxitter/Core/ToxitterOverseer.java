package Toxitter.Core;

import Toxitter.Model.annotations.FetchAt;
import Toxitter.Security.annotations.Protected;
import Toxitter.Model.annotations.RequestParam;
import Toxitter.Model.annotations.Route;
import Toxitter.Security.ToxitterSecurity;
import Toxitter.Logging.Ullog;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ToxitterOverseer
{
    public static void serve(Class c)
    {
        System.out.println("Serve class "+c.getName());
        if ( c.isAnnotationPresent(FetchAt.class) )
        {
            Ullog.put(ToxitterOverseer.class,"Found FetchAt Annotation");
            String persist = "";
            FetchAt p = (FetchAt) c.getAnnotation(FetchAt.class);
            Method[] ms = c.getMethods();
            for(Method method: ms)
            {
                if (method.isAnnotationPresent(Route.class) )
                {
                    Ullog.put(ToxitterOverseer.class, "Found Route annotation!");
                    Route r = (Route) method.getAnnotation(Route.class);
                    String allRoute = "/"+p.route()+"/"+r.route();
                    if ( method.isAnnotationPresent(Protected.class) )
                    {
                        Ullog.put(ToxitterOverseer.class,"Route is protected!");
                        Protected pr = (Protected) method.getAnnotation(Protected.class);
                        Ullog.put(ToxitterOverseer.class,"Required scope: "+pr.scope());
                        Ullog.put(ToxitterOverseer.class,"Adding to TokenHub!");
                        ToxitterSecurity.addRequiredScopeToRoute(pr.scope(),allRoute);
                    } else {
                        Ullog.put(ToxitterOverseer.class,"Route is NOT protected.");
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
            Ullog.put(ToxitterOverseer.class,"No annotation!");
        }
    }
}
