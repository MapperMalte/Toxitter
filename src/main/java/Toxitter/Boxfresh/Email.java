package Toxitter.Boxfresh;

public class Email implements Comparable
{
    private String key;

    public Email(String key)
    {
        this.key = key;
    }

    public String getMail()
    {
        return this.key;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
