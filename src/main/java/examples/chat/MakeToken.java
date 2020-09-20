package examples.chat;

public class MakeToken
{
    public static String makeToken()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 32; i++)
        {
            sb.append((int)(Math.random()*10.));
        }
        return sb.toString();
    }
}
