package Toxitter.Boxfresh.output;

import Toxitter.Annotations.core.PushTo;
import Toxitter.Core.Elemental.water.OUTPUT;
import com.google.gson.Gson;

@PushTo(route = "/login/success",method="onLoginSuccess")
public class LoginSuccess extends OUTPUT
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