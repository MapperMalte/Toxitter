package Avatar.Security;

import Avatar.Elemental.water.BookOfIlaan;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;

public class LoginHandler extends HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        if ( exchange.getRequestMethod().equals("POST") )
        {
            BookOfIlaan.write(LoginHandler.class,"handle POST ");
            System.out.println("Request Body: "+exchange.getRequestBody());
        }
    }
}