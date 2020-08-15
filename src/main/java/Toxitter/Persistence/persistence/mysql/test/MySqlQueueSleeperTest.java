package Toxitter.Persistence.persistence.mysql.test;

import Toxitter.Model.test.TestReservoirEntity;
import Toxitter.Persistence.DataAccessToReservoirEntity;
import Toxitter.Persistence.ReservoirEntityDataPresenter;
import Toxitter.Persistence.persistence.mysql.MySqlQueueSleeper;
import Toxitter.Persistence.persistence.mysql.MySqlStatement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MySqlQueueSleeperTest
{
    private ReservoirEntityDataPresenter getPresenterMock()
    {
        TestReservoirEntity test = new TestReservoirEntity("Malte",9034);
        test.setId("#id");
        DataAccessToReservoirEntity dataAccess = test.getDataAccess();
        ReservoirEntityDataPresenter depp = new ReservoirEntityDataPresenter(dataAccess);
        return depp;
    }

    @Test
    public void testInsertStatementCorrect()
    {
        MySqlQueueSleeper<String, TestReservoirEntity> mySqlQueueSleeper = new MySqlQueueSleeper<>();
        ReservoirEntityDataPresenter depp = getPresenterMock();

        MySqlStatement stmt = MySqlStatement.getInsertValueStatement(depp);
        assertEquals((stmt.prefix+stmt.middlefix+stmt.postfix),"INSERT INTO testreservoir(id, name, powerlevel)\n" +
                " VALUES ('#id', 'Malte', '9034')");
    }

    @Test
    public void testDeleteStatementCorrect()
    {
        MySqlQueueSleeper<String, TestReservoirEntity> mySqlQueueSleeper = new MySqlQueueSleeper<>();
        ReservoirEntityDataPresenter depp = getPresenterMock();

        MySqlStatement stmt = MySqlStatement.getDeleteValueStatement(depp);
        System.out.println((stmt.prefix+stmt.middlefix+stmt.postfix));
        assertEquals((stmt.prefix+stmt.middlefix+stmt.postfix),"DELETE FROM testreservoir WHERE id = '#id'");
    }
}
