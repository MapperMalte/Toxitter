package Avatar.Elemental.aether;

import Avatar.Boxfresh.routes.LoginAndRegister;
import Avatar.Boxfresh.routes.UserReservoir;
import Avatar.Annotations.core.PushTo;
import Avatar.Boxfresh.output.LoginSuccess;
import Avatar.Elemental.water.OUTPUT;
import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Security.ToxitterSecurityMiddleware;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class ToxitterWebsocketHandler extends WebSocketServer
{

    public ToxitterWebsocketHandler(int port) throws UnknownHostException
    {
        super(new InetSocketAddress(port));
    }

    public ToxitterWebsocketHandler(InetSocketAddress address)
    {
        super(address);
    }

    public ToxitterWebsocketHandler(int port, Draft_6455 draft)
    {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: " + handshake
                .getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println(
                conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    public static void push(String userId, OUTPUT outputDTO)
    {
        BookOfIlaan.write(ToxitterWebsocketHandler.class,"==== {PUSH! Called Targeting user with ID "+userId+" and outputDTO "+outputDTO.getClass().getName());
        if ( outputDTO.getClass().isAnnotationPresent(PushTo.class) )
        {
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"Found PushTo Annotation!");
            PushTo pushTo = outputDTO.getClass().getAnnotation(PushTo.class);
            BookOfIlaan.write(ToxitterWebsocketHandler.class, "Pushing to client data "+outputDTO.asJSON()+" on route "+pushTo.route());
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"UserId of target client: "+userId);
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"Associated websocket: "+ OnlineStateManager.getWebsocketByUserId(userId));
            OnlineStateManager.getWebsocketByUserId(userId).send(pushTo.route()+" "+outputDTO.asJSON());
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"PushTo Annotation for route "+pushTo.route()+" resolved. }====");
            OnlineStateManager.ping(userId);
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        System.out.println(conn + " has left the room!");
        OnlineStateManager.disconnect(conn);
    }

    private boolean knownRoute(String route)
    {
        return BookOfGorlon.routeSignatures.containsKey(route);
    }

    public static WebSocket current;

    @Override
    public synchronized void onMessage(WebSocket conn, String message)
    {
        current = conn;
        String route = message.substring(0,message.indexOf(" "));
        if ( knownRoute(route) )
        {
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"Found route: "+route);
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"Full message: "+message);
            ToxitterModelSignature tms = BookOfGorlon.routeSignatures.get(route);
            String json = message.substring(message.indexOf(" ")+1);
            BookOfIlaan.write("JSON: "+json);

            JsonObject jsonObject = new Gson().fromJson(json,JsonObject.class);
            String token = ToxitterSecurityMiddleware.extractPostParam(jsonObject,ToxitterSecurityMiddleware.TOKEN_IDENTIFIER_2);
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"Extracted token: "+token);

            if ( !LoginAndRegister.hasAccesToRoute(token,route) )
            {
                BookOfIlaan.write(ToxitterWebsocketHandler.class,"Token does not have access to route!");
                BookOfIlaan.write("Required privilege: "+ LoginAndRegister.getRequiredPrivilege(route));
                conn.send("You do not have access to this route!");

                return;
            }

            ToxitterHttpHandler.extractJsonParametersIntoToxitterModelSignature(tms, json);
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"Signature: "+tms.toString());
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"Complete: "+tms.isComplete());

            Object[] args = tms.splurpIntoParameters();
            tms.releaseForNextRequest();
            try {
                BookOfIlaan.write(ToxitterWebsocketHandler.class,"Invoking Method "+tms.getMethod().name+" on class "+tms.toxiClass.getCanonicalName());
                Object result = (tms.getMethod().method.invoke(tms.toxiClass, args));
                if ( !(result == null) )
                if ( result.getClass().equals(String.class) )
                {
                    BookOfIlaan.write(ToxitterWebsocketHandler.class,"Response from Server: "+result);
                } else
                {
                    OUTPUT response = (OUTPUT)(tms.getMethod().method.invoke(tms.toxiClass, args));
                    BookOfIlaan.write(ToxitterWebsocketHandler.class,"Response from Server: "+response.asJSON());
                    if ( response.getClass().equals(LoginSuccess.class) )
                    {
                        OnlineStateManager.connect(conn, UserReservoir.getUserByUserId(((LoginSuccess)(response)).userId));
                    }
                    if ( response.getClass().isAnnotationPresent(PushTo.class) )
                    {
                        BookOfIlaan.write(ToxitterWebsocketHandler.class,"Found PushTo annotation in OnMessage Response!");
                        PushTo pushTo = response.getClass().getDeclaredAnnotation(PushTo.class);
                        BookOfIlaan.write(ToxitterWebsocketHandler.class,"Pushing to route "+pushTo.route()+" data "+response.asJSON());
                        conn.send(pushTo.route()+" "+response.asJSON());
                    } else {
                        BookOfIlaan.write(ToxitterWebsocketHandler.class,"Not sending response back to client, because client is connected via websocket");
                    }
                }
                //conn.send("/login/success/ "+response);
            } catch (Exception e) {
                e.printStackTrace();
                conn.send("Sth. went wrong!");
            }
        } else {
            BookOfIlaan.write(ToxitterWebsocketHandler.class,"Unknown route "+route+" from message "+message);
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message)
    {
        broadcast(message.array());
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex)
    {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart()
    {
        System.out.println("Websocket Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(1000);
    }
}