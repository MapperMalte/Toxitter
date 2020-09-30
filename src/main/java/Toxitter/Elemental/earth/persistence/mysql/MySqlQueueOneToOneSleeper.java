package Toxitter.Elemental.earth.persistence.mysql;

import Toxitter.Elemental.earth.ReservoirEntity;
import Toxitter.Elemental.water.DataAccessToReservoirEntity;
import Toxitter.Elemental.water.ReservoirEntityDataPresenter;
import Toxitter.Elemental.water.QueueSleeper;

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