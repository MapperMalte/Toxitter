package Avatar.Elemental.aether;

import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.water.OUTPUT;
import Avatar.Elemental.water.Pusher;

public class WebsocketPusher implements Pusher {
    @Override
    public void push(User user, OUTPUT outputDTO)
    {
        WebsocketConnection websocketConnection = ((WebsocketConnection)ConnectionManager.getConnectionByType(user,WebsocketConnection.class));
        websocketConnection.getSocket().send(outputDTO.asJSON());
    }
}
