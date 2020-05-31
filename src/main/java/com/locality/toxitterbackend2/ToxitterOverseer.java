package com.locality.toxitterbackend2;

import ToxitterModel.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ToxitterOverseer
{
    public static void serve(Class c)
    {
        System.out.println("Serve class "+c.getName());
        if ( c.isAnnotationPresent(FetchAt.class) )
        {
            System.out.println("Found FetchAt Annotaton");
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
                        System.out.println("Route is protected!");
                        Protected pr = (Protected) method.getAnnotation(Protected.class);
                        System.out.println("Required scope: "+pr.scope());
                        System.out.println("Adding to TokenHub!");
                        ToxitterSecurity.addRequiredScopeToRoute(pr.scope(),allRoute);
                    } else {
                        System.out.println("Route is not protected");
                    }
                    System.out.println("Route in Overseer: "+allRoute);
                    System.out.println("Param Names: ");
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
            System.out.println("No annotation");
        }
    }
}
