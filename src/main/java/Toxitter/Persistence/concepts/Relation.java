package Toxitter.Persistence.concepts;

public abstract class Relation<K,V> {
    public abstract void put(K key, V value);
}
