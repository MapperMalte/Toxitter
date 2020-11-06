package Avatar.Boxfresh.routes;

import Avatar.Annotations.security.Hidden;
import Avatar.Annotations.security.Personal;
import Avatar.Boxfresh.reservoirs.UserReservoir;
import Avatar.Elemental.earth.ID;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Annotations.core.FetchAt;
import Avatar.Elemental.earth.Persist;
import Avatar.Annotations.security.Protected;
import Avatar.Annotations.core.Route;
import Avatar.Elemental.fire.AI.stimulanziae.Investin;
import Avatar.Elemental.water.html.annotations.Button;
import Avatar.Elemental.water.html.annotations.Content;
import Avatar.Elemental.water.html.annotations.Header;

@FetchAt(route = "user")
@Persist(primaryKey = "userId", tableName = "user")
public class User extends ReservoirEntity
{
    public String userId;
    public @Header String name;
    public @Header String photoUrl = "";
    public @Content String description = "";
    public @Hidden String pwd;
    public @Personal String email;

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
    @Button(buttonText = "Foto hochladen")
    @Personal
    public static String setPhotoUrl(
            String userId,
            String photoUrl
    )
    {
        UserReservoir.getUserByUserId(userId).photoUrl = photoUrl;
        return photoUrl;
    }
}