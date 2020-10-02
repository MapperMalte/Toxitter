package Avatar.Elemental.water;

import Avatar.Elemental.earth.ReservoirEntity;
import com.google.gson.Gson;

public abstract class OUTPUT extends ReservoirEntity
{
    public String asJSON()
    {
        return new Gson().toJson(this).toString();
    }
}
