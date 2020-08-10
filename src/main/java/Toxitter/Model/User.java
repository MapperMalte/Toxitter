package Toxitter.Model;

import Toxitter.Model.annotations.FetchAt;
import Toxitter.Model.concepts.ReservoirEntity;
import Toxitter.Persistence.annotations.Table;
import Toxitter.Security.annotations.Protected;
import Toxitter.Model.annotations.RequestParam;
import Toxitter.Model.annotations.Route;
import Toxitter.Model.concepts.Reservoir;

@FetchAt(route = "user")
@Table(primaryKey = "userId", tableName = "user")
public class User extends ReservoirEntity
{
    String userId;
    String name;
    public String photoUrl = "";
    String description = "";
    String pwd;
    String email;

    /*
        INSERT INTO toxitter.user
        VALUES('bjkdfgdfhjfdfgs667',"Malte",null,null,"34534dfg","malte_nolden@yahoo.de");
     */

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
