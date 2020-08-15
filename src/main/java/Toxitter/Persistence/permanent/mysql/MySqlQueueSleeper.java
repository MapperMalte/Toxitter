package Toxitter.Persistence.permanent.mysql;

import Toxitter.Model.ID;
import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.DataAccessToReservoirEntityBeingPersisted;
import Toxitter.Persistence.ReservoirEntityDataPresenter;
import theory.DiamondList;
import theory.QueueSleeper;

public class MySqlQueueSleeper<K extends ID, V extends ReservoirEntity> extends QueueSleeper<K, V>
{
    MySqlShapeshifter shift;

    @Override
    public void putToSleep(K key, V value)
    {
        ReservoirEntityDataPresenter representation = new ReservoirEntityDataPresenter(new DataAccessToReservoirEntityBeingPersisted(value));
        MySqlStatement stmt = shift.getInsertValueStatement(representation,key,value);
        MySql.execute(stmt);
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