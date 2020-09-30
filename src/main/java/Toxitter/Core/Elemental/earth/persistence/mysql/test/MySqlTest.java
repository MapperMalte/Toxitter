package Toxitter.Core.Elemental.earth.persistence.mysql.test;

import Toxitter.test.TestReservoirEntity;
import Toxitter.Core.Elemental.water.ReservoirEntityDataPresenter;
import Toxitter.Core.Elemental.earth.persistence.mysql.MySql;
import Toxitter.Core.Elemental.earth.persistence.mysql.MySqlStatement;
import org.junit.Test;

public class MySqlTest
{
    //@Test
    public void testMysqlConnection()
    {
        MySql mySql = new MySql();
        //MySql.create();

        ReservoirEntityDataPresenter depp = MySqlStatementTest.getPresenterMock();
        MySqlStatement stmt = MySqlStatement.getCreateTableStatement(depp);

        MySql.execute(stmt);
    }

    //@Test
    public void testMakeAndReadSomeData()
    {
        ReservoirEntityDataPresenter depp = MySqlStatementTest.getPresenterMock();
        MySqlStatement stmt = MySqlStatement.getInsertValueStatement(depp);
        MySql.execute(stmt);
    }

    @Test
    public void testReadModel()
    {
        TestReservoirEntity test = new TestReservoirEntity();
        test.setId("#id");
        MySql.selectIntoReservoirEntity(test);
        System.out.println("ID: "+test.getId());
        System.out.println("Name: "+test.getName());
        System.out.println("Lvl: "+test.getPowerlevel());
    }
}
