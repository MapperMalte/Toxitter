package Avatar.Elemental.aether;

import Avatar.Boxfresh.routes.User;
import org.java_websocket.WebSocket;

import java.util.TreeMap;

public class OnlineStateManager
{
    private static TreeMap<String, User> onlineUsers = new TreeMap<>();
    private static TreeMap<String,WebSocket> websocketsByUserId = new TreeMap<>();
    private static TreeMap<String, Long> lastPing = new TreeMap<>();

    public static boolean isUserOnline(String userId)
    {
        return websocketsByUserId.containsKey(userId);
    }

    public static void connect(WebSocket webSocket, User user)
    {
        System.out.println("Connecting user "+user.name+" with IP "+webSocket.getRemoteSocketAddress());
        websocketsByUserId.put(user.userId,webSocket);
        onlineUsers.put(webSocket.getRemoteSocketAddress().getHostString(),user);
        ping(user.userId);
        //Pool.fetchForUser(user.userId);
    }

    public static void ping(String userId)
    {
        lastPing.put(userId,System.currentTimeMillis());
    }

    public static WebSocket getWebsocketByUserId(String userId)
    {
        return websocketsByUserId.get(userId);
    }
    
    public static void disconnect(WebSocket webSocket)
    {
        User user = onlineUsers.get(webSocket.getRemoteSocketAddress().getHostString());
        onlineUsers.remove(webSocket.getRemoteSocketAddress().getHostString());
        websocketsByUserId.remove(user.userId);
    }
}
