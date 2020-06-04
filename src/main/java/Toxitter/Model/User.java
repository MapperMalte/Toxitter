package Toxitter.Model;

import Toxitter.Model.annotations.FetchAt;
import Toxitter.Security.annotations.Protected;
import Toxitter.Model.annotations.RequestParam;
import Toxitter.Model.annotations.Route;
import Toxitter.Model.concepts.Reservoir;

@FetchAt(route = "user")
public class User
{
    String userId;
    String name;
    public String photoUrl = "";
    String description = "";
    String pwd;
    String email;

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return userId;
    }

    public static String getPwdHashMock()
    {
        return Reservoir.makeId();
    }

    public boolean pwdCorrect(String pwd)
    {
        return this.pwd.equals(pwd);
    }

    @Route(route = "setdescription")
    @Protected(scope = "user")
    public static String setDescription(
            @RequestParam(name = "userId",obligatory = true) String userId,
            @RequestParam(name = "description",obligatory = true) String description
    )
    {
        UserReservoir.getUserByUserId(userId).description = description;
        return description;
    }

    @Route(route = "setprofilephoto")
    @Protected(scope = "user")
    public static String setPhotoUrl(
            @RequestParam(name = "userId",obligatory = true) String userId,
            @RequestParam(name = "photoUrl",obligatory = true) String photoUrl
    )
    {
        UserReservoir.getUserByUserId(userId).photoUrl = photoUrl;
        return photoUrl;
    }
}
