package Toxitter.Model;

public abstract class Relation<K extends Comparable,V>
{
    public abstract void update(K key, V newValue);
    public abstract void put(K key, V value);
    public abstract Object read(K key);
    public abstract void delete(K key);
}
