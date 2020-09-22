package Toxitter.Core.realtime;

import org.java_websocket.WebSocket;

public interface DisconnectHandler {
    public void onDisconnect(String userId, WebSocket webSocket);
}
