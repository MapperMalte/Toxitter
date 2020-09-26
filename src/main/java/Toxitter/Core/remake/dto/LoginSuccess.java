package Toxitter.Core.remake.dto;

import Toxitter.Core.annotations.PushTo;
import Toxitter.Core.remake.dto.output.Transferrable;
import com.google.gson.Gson;

@PushTo(route = "/login/success",method="onLoginSuccess")
public class LoginSuccess implements Transferrable
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

    @Override
    public String asJavaScript() {
        return null;
    }

    @Override
    public String asJavaCode() {
        return null;
    }
}