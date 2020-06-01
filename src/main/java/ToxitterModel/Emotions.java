package ToxitterModel;

import java.util.TreeMap;

public class Emotions
{
    public static final String emotions
            = ":heart_eyes::heart::rage::joy::cry::flushed::satisfied:";
    private static TreeMap<String,Integer> data = new TreeMap<>();
    private static int count = 0;
    private static boolean initialized = false;

    public static int getEmotionCount()
    {
        checkInit();
        return count;
    }

    public static void init()
    {
        String[] emotions = Emotions.emotions.split(":");
        for (int i = 0; i < emotions.length; i++)
        {
            System.out.println("Emotion "+i+": "+emotions[i]);
            data.put(emotions[i],i);
            count++;
        }
    }

    private static synchronized void checkInit()
    {
        if ( !initialized )
        {
            init();
            initialized = true;
        }
    }

    /**
     *
     * @param emotion
     * @return -1 if key is not contained
     */
    public static int getEmotionId(String emotion)
    {
        checkInit();
        if ( !data.containsKey(emotion) ) return -1;
        return data.get(emotion);
    }
}
