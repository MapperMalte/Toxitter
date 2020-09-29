package Toxitter.Boxfresh.routes;

import Toxitter.Model.elemental.matter.ID;
import Toxitter.Model.elemental.matter.ReservoirEntity;
import Toxitter.Annotations.core.FetchAt;
import Toxitter.Annotations.persistence.Persist;
import Toxitter.Annotations.security.Protected;
import Toxitter.Annotations.core.Route;

@FetchAt(route = "user")
@Persist(primaryKey = "userId", tableName = "user")
public class User extends ReservoirEntity
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
            String userId,
            String description
    )
    {
        UserReservoir.getUserByUserId(userId).description = description;
        return description;
    }

    @Route(route = "setprofilephoto")
    @Protected(scope = "user")
    public static String setPhotoUrl(
            String userId,
            String photoUrl
    )
    {
        UserReservoir.getUserByUserId(userId).photoUrl = photoUrl;
        return photoUrl;
    }


}
