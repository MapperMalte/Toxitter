package Avatar.Elemental.aether;


import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.water.OUTPUT;
import Avatar.Elemental.water.Pusher;
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

    @Override
    public String getSourceIdentifier() {
        return webSocket.getResourceDescriptor();
    }
}
