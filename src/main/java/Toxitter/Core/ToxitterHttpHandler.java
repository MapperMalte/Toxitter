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

public class ToxitterHttpHandler implements HttpHandler
{
    private void sendWithCode(HttpExchange httpExchange, String response, int code) throws IOException {
        response = Umlauter.umlaut(response);
        Headers headers= httpExchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        OutputStream outputStream = httpExchange.getResponseBody();
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(code, bytes.length);
        /*
        for(int i = 0; i < bytes.length; i++ )
        {
            outputStream.write(bytes,i*256,Math.min(256,bytes.length-256*i));
        }*/
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    private void cancelAs403NotAllowed(HttpExchange httpExchange) throws IOException {
        sendWithCode(httpExchange,"Not allowed!",403);
    }

    private void cancelAs404NotFound(HttpExchange httpExchange) throws IOException {
        sendWithCode(httpExchange,"Route not found!",404);
    }

    private void cancelAs502InternalServerError(HttpExchange httpExchange, String msg) throws IOException {
        sendWithCode(httpExchange,"Internal Server Error! Message: "+msg,502);
    }

    private void sendStringAs200Success(HttpExchange httpExchange, String response) throws IOException {
        sendWithCode(httpExchange,response,200);
    }

    /**
     * @param tms
     * @param route
     * @param params
     * @param paramOffset
     */
    private void extractRouteParametersIntoToxitterModelSignature(ToxitterModelSignature tms, String route, String[] params, int paramOffset)
    {
        ReplenisherStack<ToxitterModelSignature.Method> methods = tms.getReplenisherStack();
        for(int i = paramOffset; i < params.length; i++)
        {
            String[] data = params[i].split("=");
            ToxitterModelSignature.Method m = methods.find(data[0]);
            if ( m!= null )
            {
                m.value = data[1];
            }
        }
    }

    private boolean knownRoute(String route)
    {
        return ToxitterServer.routes.containsKey(route);
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        String uri = httpExchange.getRequestURI().toString();
        String[] params = uri.split("\\?");
        String route = params[0];
        Ullog.put(ToxitterHttpHandler.class,"Handling Request on uri: "+uri);
        if (!knownRoute(route) )
        {
            Ullog.put(ToxitterHttpHandler.class,"Route "+route+" unknown!");
            cancelAs404NotFound(httpExchange);
            return;
        }
        if ( !ToxitterSecurityMiddleware.allowed(route,uri) )
        {
            cancelAs403NotAllowed(httpExchange);
            return;
        }

        ToxitterModelSignature tms = ToxitterServer.routes.get(route);
        extractRouteParametersIntoToxitterModelSignature(tms,route,params,1);
        Object[] args = tms.splurpIntoParameters();
        tms.releaseForNextRequest();

        ToxitterSessionReservoir.registerRequest(tms,httpExchange.getRemoteAddress().getAddress().toString());

        String response = null;
        try {
            response = tms.getMethod().method.invoke(tms.toxiClass, args).toString();
            Ullog.put(ToxitterHttpHandler.class,"Response from Server: "+response);
            sendStringAs200Success(httpExchange,response);
        } catch (Exception e) {
            e.printStackTrace();
            cancelAs502InternalServerError(httpExchange,"Sth went wrong!");

        }
    }

}
