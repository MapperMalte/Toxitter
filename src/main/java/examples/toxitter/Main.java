package examples.toxitter;

public class Main
{
    public static void main(String[] args)
    {
        try {
            ToxitterStandardConfiguratedEnvironment.up();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
