package Toxitter.Model;

import java.util.TreeMap;

public class Emotions
{
    public static final String emotions
            = ":heart_eyes::heart::rage::joy::cry::flushed::satisfied:";
    private static TreeMap<String,Smiley> data = new TreeMap<>();
    private static int count = 0;
    private static boolean initialized = false;

    public static class Smiley
    {
        String name;
        String html;

        public Smiley(String name, String html)
        {
            this.name = name;
            this.html = html;
        }
    }

    public static int getEmotionCount()
    {
        checkInit();
        return count;
    }

    private static void addSmiley(String name, String html, String html2)
    {
        data.put(name,new Smiley(name,html));
    }

    public static boolean isValidEmotion(String name)
    {
        return data.containsKey(name);
    }

    public static void init()
    {
        addSmiley("satisfied","&#128518;", "&#x1f606;");
        addSmiley("flushed","&#128563;", "&#x1f633;");
        addSmiley("cry","&#128546;", "&#x1f622;");
        addSmiley("joy","&#128514;", "&#x1f602;");
        addSmiley("rage","&#128545;", "&#x1f621;");
        addSmiley("heart","❤", "&#65039;");
        addSmiley("heart_eyes","&#128525;", "&#x1f60d;");
    }

    private static synchronized void checkInit()
    {
        if ( !initialized )
        {
            init();
            initialized = true;
        }
    }
}
