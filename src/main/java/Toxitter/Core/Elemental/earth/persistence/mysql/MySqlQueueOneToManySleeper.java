package Toxitter.Core.Elemental.earth.persistence.mysql;

import Toxitter.Core.Elemental.earth.ReservoirEntity;
import Toxitter.Core.Elemental.water.DataAccessToReservoirEntity;
import Toxitter.Core.Elemental.water.ReservoirEntityDataPresenter;
import Toxitter.Core.Elemental.water.QueueSleeper;

public class MySqlQueueOneToManySleeper<K extends Comparable, V extends ReservoirEntity> extends QueueSleeper<K, V>
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