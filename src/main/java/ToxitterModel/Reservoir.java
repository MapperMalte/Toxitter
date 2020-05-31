package ToxitterModel;

public abstract class Reservoir
{
    public static final String makeId()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 32; i++)
        {
            sb.append((char)(97+(Math.random()* (122-97))));
        }
        return sb.toString();
    }

    public void flash()
    {

    }
}
