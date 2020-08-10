package Toxitter.Model;

// Uses Temproal Lists and a Mysql-Connector or SimpleDB-Connector to dynamically
// store and fetch dats
public class Reservoir<K extends Comparable,V> extends Relation<K,V>
{
    public static final String makeId()
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 32; i++)
        {
            sb.append((char)(97+(Math.random()* (122-97))));
        }
        return sb.toString();
    }

    @Override
    public void update(K key, V newValue)
    {

    }

    @Override
    public void put(K key, V value)
    {

    }

    @Override
    public Object read(K key)
    {
        return null;
    }

    @Override
    public void delete(K key)
    {

    }
}