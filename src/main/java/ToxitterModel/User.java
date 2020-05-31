package ToxitterModel;

public class User
{
    String userId;
    String name;
    String photoUrl = "";
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
}
