package Avatar.Elemental.aether;


import org.java_websocket.WebSocket;

public class WebsocketConnection implements Connectable
{
    private WebSocket webSocket;

    public WebsocketConnection(WebSocket webSocket)
    {
        this.webSocket = webSocket;
    }

    public WebSocket getSocket() {
        return webSocket;
    }
}
