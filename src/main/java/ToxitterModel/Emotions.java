package ToxitterModel;

import java.util.TreeMap;

public class Emotions
{
    public static final String emotions= ":grinning::kissing_closed_eyes::sweat_smile::disappointed::smirk::rage::scream::cry::smiley::see_no_evil::stuck_out_tongue_winking_eye::sob::smile::pensive::+1::blush::joy::heart_eyes::unamused::flushed::heart::wink::relaxed::relieved::speak_no_evil::kissing_heart::confused::kiss::grin::stuck_out_tongue_closed_eyes::blush::weary::persevere:";
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
            data.put(emotions[i],i);
            count++;
        }
    }

    private static synchronized void checkInit()
    {
        if ( !initialized )
        {
            initialized = true;
            init();
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
