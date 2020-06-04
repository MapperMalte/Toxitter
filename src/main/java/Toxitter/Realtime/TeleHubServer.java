package Toxitter.Realtime;

import com.sun.xml.internal.ws.api.message.Message;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/endpoint/{username}")
public class TeleHubServer {

    public static void main()
    {

    }


    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException
    {
        System.out.println("Connected: "+session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException
    {
        System.out.println("OnMessage id "+session.getId()+" message "+message);
        session.getBasicRemote().sendText("Erhielt message: "+message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}