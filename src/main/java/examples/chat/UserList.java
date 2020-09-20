package examples.chat;

import org.java_websocket.WebSocket;
import theory.DiamondList;

public class UserList
{
    private static Reservoir<String, User> users = new Reservoir<>();
    private static Reservoir<String, OnlineUser> onlineUserReservoir = new Reservoir<>();
    private static Reservoir<String, User> userByWebsocket = new Reservoir<>();
    private static Reservoir<String, FriendRequestDTO> sentFriendRequests = new Reservoir<>();
    private static Reservoir<String, FriendRequestDTO> unansweredReceivedFriendRequests = new Reservoir<>();

    public static User get(WebSocket webSocket)
    {
        return userByWebsocket.get(webSocket.getRemoteSocketAddress().toString());
    }

    public static DiamondList<User> getFriendsList()
    {
        return null;
    }

    public static boolean fulfillAcceptFriendRequestDTO(FriendRequestDTO friendRequestDTO)
    {
        FriendRequestDTO friendRequestDTO1 = unansweredReceivedFriendRequests.get(friendRequestDTO.to);
        //friends.put(friendRequestDTO.to,friendRequestDTO.from);
        //friends.put(friendRequestDTO.from,friendRequestDTO.to);
        User to = users.get( friendRequestDTO.to );
        User from = users.get(friendRequestDTO.from );
        from.webSocket.send("#FRIEND_REQUEST_ACCEPTED "+to.name);
        return true;
    }

    public static boolean fulfillSendFriendRequestDTO(FriendRequestDTO friendRequestDTO)
    {
        User to = users.get( friendRequestDTO.to );
        User from = users.get(friendRequestDTO.from );
        sentFriendRequests.put(friendRequestDTO.from,friendRequestDTO);
        unansweredReceivedFriendRequests.put(friendRequestDTO.to,friendRequestDTO);
        to.webSocket.send("#RECEIVED_FRIEND_REQUEST_BY "+from.name);
        return true;
    }

    public static boolean fulfillLoginDTO(LoginDTO loginDTO)
    {
        if ( users.containsKey(loginDTO.username) )
        {
            User user = users.get(loginDTO.username);
            if ( user.password.equals(loginDTO.password) )
            {
                loginDTO.token = MakeToken.makeToken();
                userByWebsocket.put(loginDTO.webSocket.getRemoteSocketAddress().toString(), user);
                user.webSocket = loginDTO.webSocket;
                loginDTO.messageAnswer = "Login successful! Welcome "+loginDTO.username;
                return true;
            } else
            {
                loginDTO.messageAnswer = "Incorrect password!";
                return false;
            }
        } else
        {
            User newUser = new User();
            newUser.password = loginDTO.password;
            newUser.name = loginDTO.username;
            newUser.webSocket = loginDTO.webSocket;
            loginDTO.messageAnswer = "Welcome! You have been registered "+newUser.name;
            userByWebsocket.put(loginDTO.webSocket.getRemoteSocketAddress().toString(), newUser);
            users.put(newUser.name,newUser);
            return true;
        }
    }
}