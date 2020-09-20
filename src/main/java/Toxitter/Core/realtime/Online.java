package Toxitter.Core.realtime;

import Toxitter.Core.User;
import org.java_websocket.WebSocket;

import java.util.TreeMap;

public class Online
{
    private static TreeMap<WebSocket, User> onlineUsers = new TreeMap<>();
    private static TreeMap<String,WebSocket> websocketsByUserId = new TreeMap<>();

    public static void connect(WebSocket webSocket, User user)
    {
        websocketsByUserId.put(user.userId,webSocket);
        onlineUsers.put(webSocket,user);
    }

    public static WebSocket getWebsocketByUserId(String userId)
    {
        return websocketsByUserId.get(userId);
    }
    
    public static void disconnect(WebSocket webSocket)
    {
        onlineUsers.remove(webSocket);
    }
}
