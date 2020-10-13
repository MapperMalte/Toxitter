package Avatar.Elemental.water;

import Avatar.Elemental.wind.artifacts.DiamondList;
import com.google.gson.*;

import java.lang.reflect.Type;

public class DiamondListAdapter implements JsonSerializer<DiamondList>
{
    @Override
    public JsonElement serialize(DiamondList diamondList, Type type, JsonSerializationContext jsonSerializationContext)
    {
        JsonObject jsonMerchant = new JsonObject();
        Gson gson = new Gson();
        JsonArray arr = new JsonArray();
        diamondList.bottom();
        while( !diamondList.isPointerNull() )
        {
            arr.add(gson.toJsonTree(diamondList.getCurrent()));
            diamondList.next();
        }
        return arr;
    }
}
