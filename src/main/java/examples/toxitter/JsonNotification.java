package examples.toxitter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class JsonNotification
{
    public String text;

    public static String toJson(JsonNotification[] jsonNotifications)
    {
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        for (JsonNotification notif: jsonNotifications)
        {
            jsonArray.add(notif.text);
        }
        return gson.toJson(jsonArray);
    }
}
