package Toxitter.Model;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class UserNotificationCollector
{
    public static final int EXPECTED_REACTION_COUNT = 10;
    private String userId;
    private static TreeMap<String,UserNotificationCollector> data = new TreeMap<>();
    private TreeMap<String, ArrayList<Reaction>> reactions = new TreeMap<>();

    private static class Reaction {
        String reactingUserId;
        String reaction;
        boolean seen = false;

        Reaction(String reactingUserId, String reaction)
        {
            this.reactingUserId = reactingUserId;
            this.reaction = reaction;
        }
    }

    public static UserNotificationCollector byUserId(String userId)
    {
        if ( data.containsKey(userId) )
        {
            return data.get(userId);
        } else {
            UserNotificationCollector notificationCollector = new UserNotificationCollector();
            data.put(userId, notificationCollector);
            return notificationCollector;
        }
    }

    /**
     *
     * @param p
     * @param reactingUserId
     * @param reaction
     */
    public void registerReactionToPost(Post p, String reactingUserId, String reaction)
    {
        if ( reactions.containsKey(p.id) ) {
            ArrayList<Reaction> reactionList = reactions.get(p.id);
            reactionList.add(new Reaction(reactingUserId, reaction));
        } else {
            ArrayList<Reaction> reactionList = new ArrayList<>(EXPECTED_REACTION_COUNT);
            reactionList.add(new Reaction(reactingUserId, reaction));
            reactions.put(p.id,reactionList);
        }
    }

    public JsonNotification[] fetchAllNotifications()
    {
        ArrayList<JsonNotification> notifications = new ArrayList<>(20);
        int count = 0;
        for (Map.Entry<String,ArrayList<Reaction>> entry : reactions.entrySet()) {
            Iterator<Reaction> it = entry.getValue().iterator();
            int countReactionsToPost = 0;
            String[] names = new String[4];
            while (it.hasNext())
            {
                if ( countReactionsToPost < 4 )
                {
                    names[countReactionsToPost] = UserReservoir.getUserByUserId(it.next().reactingUserId).name;
                }
                countReactionsToPost++;
            }
            String nameStr = "";

            switch (countReactionsToPost)
            {
                case 0:
                    break;
                case 1:
                    nameStr = names[0]+" hat";
                    break;
                case 2:
                    nameStr = names[0]+ " und "+names[1]+" haben";
                    break;
                case 3:
                    nameStr = names[0]+ ", "+names[1] + " und "+names[2]+" haben";
                    break;
                case 4:
                    nameStr = names[0]+ ", "+names[1] + ", " +names[2]+ " und "+names[3]+" haben";
                    break;
                default:
                    nameStr  = names[0]+ ", "+names[1] + ", " +names[2]+ ", "+names[3]+" und "+(countReactionsToPost-4)+" weitere haben";
            }
            JsonNotification notif = new JsonNotification();
            notif.text = nameStr + " auf deinen Beitrag "+PostReservoir.getPostByPostId(entry.getKey()).title+" reagiert.";
            notifications.add(notif);
            count++;
        }

        JsonNotification notif = new JsonNotification();
        notif.text= "Willkommen auf Toxitter!";
        notifications.add(notif);
        count++;

        JsonNotification[] notifs = new JsonNotification[count];

        return notifications.toArray(notifs);
    }
}
