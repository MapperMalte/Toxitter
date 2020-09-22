package Toxitter.Core.realtime;

import Toxitter.Core.User;
import org.java_websocket.WebSocket;

import java.util.TreeMap;

public class OnlineStateManager
{
    private static TreeMap<String, User> onlineUsers = new TreeMap<>();
    private static TreeMap<String,WebSocket> websocketsByUserId = new TreeMap<>();

    public static void connect(WebSocket webSocket, User user)
    {
        System.out.println("Connecting user "+user.name+" with IP "+webSocket.getRemoteSocketAddress());
        websocketsByUserId.put(user.userId,webSocket);
        onlineUsers.put(webSocket.getRemoteSocketAddress().getHostString(),user);
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
