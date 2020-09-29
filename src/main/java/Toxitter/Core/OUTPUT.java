package Toxitter.Core;

import Toxitter.Model.elemental.matter.ReservoirEntity;
import com.google.gson.Gson;

public abstract class OUTPUT extends ReservoirEntity
{
    public String asJSON()
    {
        return new Gson().toJson(this).toString();
    }
}
