package Toxitter.Persistence.persistence.mysql;

import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.DataAccessToReservoirEntity;
import Toxitter.Persistence.ReservoirEntityDataPresenter;
import theory.QueueSleeper;

public class MySqlQueueOneToOneSleeper<K extends Comparable, V extends ReservoirEntity> extends QueueSleeper<K, V>
{

    @Override
    public void putToSleep(K key, V value)
    {
        ReservoirEntityDataPresenter representation = new ReservoirEntityDataPresenter(new DataAccessToReservoirEntity(value));
        MySqlStatement stmt = MySqlStatement.getInsertValueStatement(
                representation);
        MySql.execute(stmt);
    }

    @Override
    public V wakeup(K key) {
        return null;
    }

    @Override
    public void delete(K key) {

    }
}