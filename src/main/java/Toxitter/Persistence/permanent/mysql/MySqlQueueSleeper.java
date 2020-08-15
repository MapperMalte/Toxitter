package Toxitter.Persistence.permanent.mysql;

import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.Persistor;
import Toxitter.Persistence.TypeTransformer;
import theory.DiamondList;
import theory.QueueSleeper;

import java.lang.reflect.Field;

public class MySqlQueueSleeper<K extends Comparable, V extends ReservoirEntity> extends QueueSleeper<K, V>
{
    Persistor<V> p;
    TypeTransformer t;

    @Override
    public void putToSleep(K key, V value)
    {
        Field[] f = value.getClass().getFields();
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "INSERT INTO table ("+value.getTable().tableName()+")\n VALUES (";
        StringBuilder sb = new StringBuilder();
        for(Field field: f)
        {
            sb.append(t.transform(field.getType())+p.get(value,field.getName()).toString());
        }
        stmt.middlefix =
        stmt.postfix = ") ON DUPLICATE KEY UPDATE\n";
    }

    @Override
    public void multiPutToSleep(DiamondList<V> values)
    {

    }

    @Override
    public V wakeup(K key) {
        return null;
    }

    @Override
    public void delete(K key) {

    }
}