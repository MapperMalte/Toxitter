package Avatar.Boxfresh.routes;

import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Annotations.core.FetchAt;
import Avatar.Elemental.earth.Persist;
import Avatar.Annotations.security.Protected;
import Avatar.Annotations.core.Route;
import Avatar.Elemental.fire.AI.stimulanziae.Investin;

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
    @Investin
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
    @Investin
    public static String setPhotoUrl(
            String userId,
            String photoUrl
    )
    {
        UserReservoir.getUserByUserId(userId).photoUrl = photoUrl;
        return photoUrl;
    }


}
