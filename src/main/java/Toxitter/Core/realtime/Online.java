package Toxitter.Core.realtime;

import Toxitter.Core.User;
import org.java_websocket.WebSocket;

import java.util.TreeMap;

public class Online
{
    private static TreeMap<WebSocket, User> onlineUsers = new TreeMap<>();

    public static void connect(WebSocket webSocket, User user)
    {
        onlineUsers.put(webSocket,user);
    }
    
    public static void disconnect(WebSocket webSocket)
    {
        onlineUsers.remove(webSocket);
    }
}
