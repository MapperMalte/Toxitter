package Toxitter2.dto;


import Toxitter2.annotations.PushTo;

@PushTo(route = "/login/answer")
public class LoginAnswerDto extends InputAtom
{
    public String accessToken;
    public String userId;
}
