package examples.chat;

import org.java_websocket.WebSocket;

public class User implements Comparable<User>
{
    String name;
    String password;
    WebSocket webSocket;

    public void sendFriendRequest(FriendRequestDTO friendRequestDTO)
    {
        webSocket.send(friendRequestDTO.from+ " hat dir eine Freundschaftsanfrage geschickt: "+friendRequestDTO.to);
    }

    @Override
    public int compareTo(User o)
    {
        return name.compareTo(o.name);
    }
}