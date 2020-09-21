package Toxitter.Core.realtime;

import Toxitter.Core.ToxitterSecurity;
import Toxitter.Core.ToxitterServer;
import Toxitter.Core.User;
import Toxitter.Core.UserReservoir;
import Toxitter.Core.http.ToxitterHttpHandler;
import Toxitter.Core.http.ToxitterModelSignature;
import Toxitter.Logging.Ullog;
import Toxitter.Security.ToxitterSecurityMiddleware;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import examples.chat.FriendRequestDTO;
import examples.chat.Reservoir;
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

    public static void push(String userId, String route, OutputDTO outputDTO)
    {
        Ullog.put(ToxitterWebsocketHandler.class, "Pushing: "+route+" "+outputDTO.asJSON());
        Online.getWebsocketByUserId(userId).send(route+" "+outputDTO.asJSON());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");
    }
    Reservoir<String, FriendRequestDTO> receivedUnresolvedFriendRequestes = new Reservoir<>();

    private boolean knownRoute(String route)
    {
        return ToxitterServer.routeSignatures.containsKey(route);
    }

    public static WebSocket current;

    @Override
    public synchronized void onMessage(WebSocket conn, String message)
    {
        current = conn;
        String route = message.substring(0,message.indexOf(" "));
        if ( knownRoute(route) )
        {
            Ullog.put(ToxitterWebsocketHandler.class,"Found route: "+route);
            ToxitterModelSignature tms = ToxitterServer.routeSignatures.get(route);
            String json = message.substring(message.indexOf(" ")+1);
            Ullog.put("JSON: "+json);
            ToxitterHttpHandler.extractJsonParametersIntoToxitterModelSignature(tms, json);
            Ullog.put(ToxitterHttpHandler.class,"Signature: "+tms.toString());
            Ullog.put(ToxitterHttpHandler.class,"Complete: "+tms.isComplete());
            JsonObject jsonObject = new Gson().fromJson(json,JsonObject.class);
            String token = ToxitterSecurityMiddleware.extractPostParam(jsonObject,ToxitterSecurityMiddleware.TOKEN_IDENTIFIER);
            Ullog.put(ToxitterWebsocketHandler.class,"Extracted token: "+token);
            if ( !ToxitterSecurity.hasAccesToRoute(token,route) )
            {
                Ullog.put(ToxitterWebsocketHandler.class,"Token does not have access to route!");
                conn.send("You do not have access to this route!");
                return;
            }
            Object[] args = tms.splurpIntoParameters();
            tms.releaseForNextRequest();
            try {
                String response = tms.getMethod().method.invoke(tms.toxiClass, args).toString();
                Ullog.put(ToxitterWebsocketHandler.class,"Invoking Method "+tms.getMethod().name+" on class "+tms.toxiClass.getCanonicalName());
                response = tms.getMethod().method.invoke(tms.toxiClass, args).toString();
                Ullog.put(ToxitterWebsocketHandler.class,"Response from Server: "+response);
                conn.send("/login/success/ "+response);
            } catch (Exception e) {
                e.printStackTrace();
                conn.send("Sth. went wrong!");
            }
        } else {
            Ullog.put(ToxitterWebsocketHandler.class,"Unknown route "+route+" from message "+message);
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