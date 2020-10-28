package Avatar.Elemental.earth;

/**
 * You can use this to connect ReservoirEntities of the same type.
 * @param <V>
 */
public class SymmetricRelation<V extends ReservoirEntity>
{
    private Relation<V,V> data;

    public SymmetricRelation()
    {
        data = new Relation<>();
    }

    /**
     * Creates a link between a and b.
     * @param a
     * @param b
     */
    public void put(V a, V b)
    {
        data.put(b,a);
        data.put(a,b);
    }

    public boolean hasLink(V a, V b)
    {
        return data.hasLink(a,b);
    }

    /**
     * Gets all ReservoirEntities that are linked with the parameter ReservoirEntity
     * @param a
     * @return
     */
    public ReservoirEntityList<V> get(V a)
    {
        return data.backwardGet(a);
    }
}
