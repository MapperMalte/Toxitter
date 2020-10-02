package Avatar.Elemental.earth.persistence.mysql.test;

import Avatar.Test.TestReservoirEntity;
import Avatar.Elemental.water.ReservoirEntityDataPresenter;
import Avatar.Elemental.earth.persistence.mysql.MySql;
import Avatar.Elemental.earth.persistence.mysql.MySqlStatement;
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
