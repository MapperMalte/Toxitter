package Toxitter.Core;

import Toxitter.Infusion.Umlauter;
import Toxitter.Logging.Ullog;
import Toxitter.Persistence.session.ToxitterSessionReservoir;
import Toxitter.Security.ToxitterSecurityMiddleware;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import theory.ReplenisherStack;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

public class ToxitterHttpHandler implements HttpHandler {

    private static TreeMap<String, ToxitterModelSignature> routes = new TreeMap<>();

    private void sendWithCode(HttpExchange httpExchange, String response, int code) throws IOException {
        response = Umlauter.umlaut(response);
        Headers headers= httpExchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        httpExchange.sendResponseHeaders(code, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

    private void cancelAs403NotAllowed(HttpExchange httpExchange) throws IOException {
        sendWithCode(httpExchange,"Not allowed!",403);
    }

    private void sendStringAs200Success(HttpExchange httpExchange, String response) throws IOException {
        sendWithCode(httpExchange,response,200);
    }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException
        {
            String[] params = httpExchange.getRequestURI().toString().split("\\?");

            ToxitterModelSignature tms = null;

            if ( !ToxitterSecurityMiddleware.allowed(params[0],httpExchange.getRequestURI().toString()) )
            {
                cancelAs403NotAllowed(httpExchange);
            }

            if ( routes.containsKey(params[0]) )
            {
                System.out.println("Route known! ");
                System.out.println("Params: ");
                tms = routes.get(params[0]);
            } else
            {
                System.out.println("Route unknown! ");
            }

            String response = "";
            ReplenisherStack<ToxitterModelSignature.Method> methods = null;
            if ( tms != null )
            {
                methods = tms.getReplenisherStack();
            }
            for(String param: params)
            {
                System.out.println("Param: "+param);
                String[] data = param.split("=");
                System.out.println("Datalength: "+data.length);
                if ( data.length > 1 )
                {
                    if ( tms != null )
                    {
                        ToxitterModelSignature.Method m = methods.find(data[0]);
                        if ( m!= null )
                        {
                            System.out.println("ToxitterMethod found: "+m.name);
                            m.value = data[1];
                        }
                    }
                    Ullog.put("Data[0]: "+data[0]+" and Data[1]: "+data[1]);
                    response += "Data[0]: "+data[0]+" and Data[1]: "+data[1]+" --- ";
                }
            }
            if ( tms != null )
            {
                System.out.println("#1 Complete: "+tms.isComplete());
                methods = tms.getReplenisherStack();
                methods.replenish();
                Object[] args = new Object[methods.getCount()];
                while(methods.getCount()>0)
                {
                    args[methods.getCount()-1] = methods.peek().value;
                    System.out.println("Key: "+methods.peek().name+" / Value: "+methods.peek().value);
                    methods.pop();
                }
                System.out.println(tms.toString());
                ToxitterSessionReservoir.registerRequest(tms,httpExchange.getRemoteAddress().getAddress().toString());
                tms.replenish();
                System.out.println("Now invoke ");
                try {
                    System.out.println("Invoking ... "+methods.peek().name);
                    String result = methods.peek().method.invoke(tms.toxiClass, args).toString();
                    response = result;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Response: "+response);
            sendStringAs200Success(httpExchange,response);
    }

}
