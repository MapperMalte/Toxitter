package Toxitter2.example;

import Toxitter.Core.annotations.FetchAt;
import Toxitter2.annotations.*;
import Toxitter2.dto.*;

public class API
{
    @Expects(dto = LoginAttemptDto.class)
    @Responds(dto = LoginAnswerDto.class)
    @FetchAt(route = "/login")
    public void login()
    {
        LoginAnswerDto loginAnswerDto = new LoginAnswerDto();
        loginAnswerDto.accessToken = "";
        loginAnswerDto.userId = "";
        loginAnswerDto.push();
    }

    @Expects(dto = CredentialsDto.class)
    @Expects(dto = ChatMessageInputAtom.class)
    public void sendMessage()
    {

    }
}
