package Toxitter.Model.elemental.matter.persistence.mysql.test;

import Toxitter.Model.test.TestReservoirEntity;
import Toxitter.Model.elemental.wind.ReservoirEntityDataPresenter;
import Toxitter.Model.elemental.matter.persistence.mysql.MySql;
import Toxitter.Model.elemental.matter.persistence.mysql.MySqlStatement;
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
