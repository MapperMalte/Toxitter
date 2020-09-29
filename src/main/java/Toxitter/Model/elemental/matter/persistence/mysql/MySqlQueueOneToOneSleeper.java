package Toxitter.Model.elemental.matter.persistence.mysql;

import Toxitter.Model.elemental.matter.ReservoirEntity;
import Toxitter.Model.elemental.wind.DataAccessToReservoirEntity;
import Toxitter.Model.elemental.wind.ReservoirEntityDataPresenter;
import Toxitter.Model.elemental.wind.QueueSleeper;

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