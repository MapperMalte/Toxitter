package Toxitter.Core;

import Toxitter.Infusion.Umlauter;
import Toxitter.Logging.Ullog;
import Toxitter.Persistence.session.ToxitterSessionReservoir;
import Toxitter.Security.ToxitterSecurity;
import Toxitter.Security.ToxitterSecurityMiddleware;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import theory.ReplenisherStack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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

    private void extractJsonParametersIntoToxitterModelSignature(ToxitterModelSignature tms, String json)
    {
        JsonObject jsonObject = new Gson().fromJson(json,JsonObject.class);
        ReplenisherStack<ToxitterModelSignature.Method> methods = tms.getReplenisherStack();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
        {
            ToxitterModelSignature.Method m = methods.find(entry.getKey());
            if ( m!= null )
            {
                m.value = entry.getValue().toString().replaceAll("\"","");
            }
        }
    }

    private boolean knownRoute(String route)
    {
        return ToxitterServer.routeSignatures.containsKey(route);
    }

    private void handlePostRequest()
    {

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        String uri = httpExchange.getRequestURI().toString();
        String route;
        int index = uri.indexOf("?");
        if ( index == -1 )
            route = uri;
        else
            route = uri.substring(0,uri.indexOf("?"));
        String[] params = null;
        String token = "";
        ToxitterModelSignature tms = ToxitterServer.routeSignatures.get(route);
        System.out.println("Incoming: "+uri);

        if ( httpExchange.getRequestMethod().equals("GET") )
        {
            params =  uri.split("\\?");
            System.out.println("Params: "+params[1]);
            System.out.println("Uri: "+uri);
            token = ToxitterSecurityMiddleware.extractGetParam(uri,ToxitterSecurityMiddleware.TOKEN_IDENTIFIER);
            System.out.println("Token: "+token);
            this.extractRouteParametersIntoToxitterModelSignature(tms,route,params,1);
            System.out.println("Uri: "+uri);

            System.out.println("INCOMING GET with token: "+token);
        } else if ( httpExchange.getRequestMethod().equals("POST") )
        {
            System.out.println("POST!");
            StringBuilder sb = new StringBuilder();
            InputStream ios = httpExchange.getRequestBody();
            int i;
            while ((i = ios.read()) != -1) {
                sb.append((char) i);
            }
            this.extractJsonParametersIntoToxitterModelSignature(tms,sb.toString());
            System.out.println("INCOMING POST: " + sb.toString());
            JsonObject jsonObject = new Gson().fromJson(sb.toString(),JsonObject.class);
            token = ToxitterSecurityMiddleware.extractPostParam(jsonObject,ToxitterSecurityMiddleware.TOKEN_IDENTIFIER);
            System.out.println("Extracted token: "+token);
        }

        Ullog.put(ToxitterHttpHandler.class,"Handling Request on uri: "+uri);
        Ullog.put(ToxitterHttpHandler.class,"Signature: "+tms.toString());
        Ullog.put(ToxitterHttpHandler.class,"Complete: "+tms.isComplete());
        if (!knownRoute(route) )
        {
            Ullog.put(ToxitterHttpHandler.class,"Route "+route+" unknown!");
            cancelAs404NotFound(httpExchange);
            return;
        }
        Ullog.put(ToxitterHttpHandler.class,"Route "+route+" known!");
        if ( !ToxitterSecurity.hasAccesToRoute(token,route) )
        {
            Ullog.put(ToxitterHttpHandler.class,"Token does not have access to route!");
            cancelAs403NotAllowed(httpExchange);
            return;
        }
        System.out.println("Splurp");

        Object[] args = tms.splurpIntoParameters();
        tms.releaseForNextRequest();

        //ToxitterSessionReservoir.registerRequest(tms,httpExchange.getRemoteAddress().getAddress().toString());

        String response = null;
        try {
            Ullog.put(ToxitterHttpHandler.class,"Invoking Method "+tms.getMethod().name+" on class "+tms.toxiClass.getCanonicalName());
            response = tms.getMethod().method.invoke(tms.toxiClass, args).toString();
            Ullog.put(ToxitterHttpHandler.class,"Response from Server: "+response);
            sendStringAs200Success(httpExchange,response);
        } catch (Exception e) {
            e.printStackTrace();
            cancelAs502InternalServerError(httpExchange,"Sth went wrong!");

        }
    }

}
