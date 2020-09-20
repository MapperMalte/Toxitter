package Toxitter.Core;

import Toxitter.Model.ID;
import Toxitter.Model.ReservoirEntity;
import Toxitter.Core.annotations.FetchAt;
import Toxitter.Persistence.annotations.Table;
import Toxitter.Security.annotations.Protected;
import Toxitter.Core.annotations.RequestParam;
import Toxitter.Core.annotations.Route;

@FetchAt(route = "user")
@Table(primaryKey = "userId", tableName = "user")
public class User extends ReservoirEntity<String>
{
    public String userId;
    public String name;
    public String photoUrl = "";
    public String description = "";
    public String pwd;
    public String email;

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return userId;
    }

    @Override
    public void setId(String key) {
        this.userId = key;
    }

    public static String getPwdHashMock()
    {
        return ID.makeId();
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
