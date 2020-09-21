package Toxitter.Core.remake.dto;

import Toxitter.Core.realtime.OutputDTO;
import org.java_websocket.WebSocket;

public class LoginSuccess extends OutputDTO
{
    String userId;
    String accessToken;
    String token;
    String userNamer;
    String photoUrl;

    @Override
    public String asJSON()
    {
        return null;
    }
}