package Toxitter.Core.realtime;

import Toxitter.Core.remake.ChatMessage_OutputDTO;
import examples.chat.FriendRequestDTO;
import examples.chat.Reservoir;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
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

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");
    }
    Reservoir<String, FriendRequestDTO> receivedUnresolvedFriendRequestes = new Reservoir<>();

    @Override
    public void onMessage(WebSocket conn, String message)
    {
        ChatMessage_OutputDTO chatMessage_outputDTO = new ChatMessage_OutputDTO();
        chatMessage_outputDTO.fromUserId = "asasdas";
        chatMessage_outputDTO.fromUserName = "Malte";
        chatMessage_outputDTO.message = message;
        conn.send(ChatMessage_OutputDTO.push(chatMessage_outputDTO));
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message)
    {
        broadcast(message.array());
        System.out.println(conn + ": " + message);
    }


    public static void main(String[] args) throws InterruptedException, IOException
    {
        int port = 8887; // 843 flash policy port
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception ex) {
        }
        ToxitterWebsocketHandler s = new ToxitterWebsocketHandler(port);
        s.start();
        System.out.println("WebsocketServer started on port: " + s.getPort());
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
        setConnectionLostTimeout(100);
    }
}