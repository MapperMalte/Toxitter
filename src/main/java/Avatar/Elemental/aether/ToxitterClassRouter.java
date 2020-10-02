package Avatar.Elemental.aether;

import Avatar.Annotations.core.FetchAt;
import Avatar.Boxfresh.routes.LoginAndRegister;
import Avatar.Annotations.route.RequestParam;
import Avatar.Annotations.security.Protected;
import Avatar.Annotations.core.Route;
import Avatar.Elemental.water.BookOfIlaan;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ToxitterClassRouter
{
    public static void serve(Class c)
    {
        System.out.println("Serve class "+c.getName());
        if ( c.isAnnotationPresent(FetchAt.class) )
        {
            BookOfIlaan.write(ToxitterClassRouter.class,"Found FetchAt Annotation");
            String persist = "";
            FetchAt p = (FetchAt) c.getAnnotation(FetchAt.class);
            Method[] ms = c.getMethods();
            for(Method method: ms)
            {
                if (method.isAnnotationPresent(Route.class) )
                {
                    BookOfIlaan.write(ToxitterClassRouter.class, "Found Route annotation!");
                    Route r = (Route) method.getAnnotation(Route.class);
                    String allRoute = "/"+p.route()+"/"+r.route();
                    if ( method.isAnnotationPresent(Protected.class) )
                    {
                        BookOfIlaan.write(ToxitterClassRouter.class,"Route is protected!");
                        Protected pr = (Protected) method.getAnnotation(Protected.class);
                        BookOfIlaan.write(ToxitterClassRouter.class,"Required scope: "+pr.scope());
                        BookOfIlaan.write(ToxitterClassRouter.class,"Adding to TokenHub!");
                        LoginAndRegister.addRequiredScopeToRoute(pr.scope(),allRoute);
                    } else {
                        BookOfIlaan.write(ToxitterClassRouter.class,"Route is NOT protected.");
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
                    BookOfGorlon.registerRoute(allRoute,sign);
                }

            }
        } else {
            BookOfIlaan.write(ToxitterClassRouter.class,"No annotation!");
        }
    }
}
