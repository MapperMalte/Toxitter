package Toxitter.Elemental.water;

import Toxitter.Elemental.earth.ReservoirEntity;
import com.google.gson.Gson;

public abstract class OUTPUT extends ReservoirEntity
{
    public String asJSON()
    {
        return new Gson().toJson(this).toString();
    }
}
