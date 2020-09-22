package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.realtime.TransferrableDataAtom;
import com.google.gson.Gson;

@PushTo(route = "/login/success",method="onLoginSuccess")
public class LoginSuccess extends TransferrableDataAtom
{
    public String userId;
    public String accessToken;
    public String token;
    public String userNamer;
    public String photoUrl;
    public String errormsg = "";

    @Override
    public String asJSON()
    {
        return new Gson().toJson(this).toString();
    }
}