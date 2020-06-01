package Toxitter.Security;

import Toxitter.Logging.Ullog;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;

public class LoginHandler extends HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        if ( exchange.getRequestMethod().equals("POST") )
        {
            Ullog.put(LoginHandler.class,"handle POST ");
            System.out.println("Request Body: "+exchange.getRequestBody());
        }
    }
}