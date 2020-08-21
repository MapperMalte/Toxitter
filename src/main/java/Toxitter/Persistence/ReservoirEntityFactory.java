package Toxitter.Persistence;

import Toxitter.Model.ReservoirEntity;

public abstract class ReservoirEntityFactory<T extends ReservoirEntity>
{
    public abstract T create();
}
