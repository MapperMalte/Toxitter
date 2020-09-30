package Toxitter.Elemental.earth;

public class SymmetricRelation<V extends ReservoirEntity>
{
    private Relation<V,V> data;

    public void put(V a, V b)
    {
        data.put(a,b);
    }

    public boolean hasLink(V a, V b)
    {
        return data.hasLink(a,b);
    }

    public ReservoirEntityList<V> get(V a)
    {
        return data.backwardGet(a);
    }
}
