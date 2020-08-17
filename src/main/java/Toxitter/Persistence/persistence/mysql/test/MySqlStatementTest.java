package Toxitter.Persistence.persistence.mysql.test;

import Toxitter.Model.test.TestReservoirEntity;
import Toxitter.Persistence.DataAccessToReservoirEntity;
import Toxitter.Persistence.ReservoirEntityDataPresenter;
import Toxitter.Persistence.persistence.mysql.MySqlStatement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MySqlStatementTest
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
    public void testCreateTableStatementCorrect()
    {
        ReservoirEntityDataPresenter depp = getPresenterMock();

        MySqlStatement stmt = MySqlStatement.getCreateTableStatement(depp);
        assertEquals(
                "CREATE TABLE `toxitter`.`testreservoir` (" +
                        "`id` VARCHAR(255) NOT NULL, " +
                        "`name` VARCHAR(255) NOT NULL, " +
                        "`powerlevel` INT NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;",
                stmt.prefix+stmt.middlefix+stmt.postfix);
    }

    @Test
    public void testUpdateStatementCorrect()
    {
        ReservoirEntityDataPresenter depp = getPresenterMock();

        MySqlStatement stmt = MySqlStatement.getUpdateValueStatement(depp);
        System.out.println(stmt.prefix+stmt.middlefix+stmt.postfix);
        assertEquals("UPDATE `toxitter`.`testreservoir`" +
                        " SET `id` = \"#id\"," +
                        " `name` = \"Malte\"," +
                        " `powerlevel` = 9034 " +
                        "WHERE `id` = \"#id\"",
                stmt.prefix+stmt.middlefix+stmt.postfix);
    }

    @Test
    public void testSelectStatementCorrect()
    {
        ReservoirEntityDataPresenter depp = getPresenterMock();

        MySqlStatement stmt = MySqlStatement.getSelectStatement(depp);
        System.out.println(stmt.prefix+stmt.middlefix+stmt.postfix);
        assertEquals("SELECT * from `toxitter`.`testreservoir` WHERE `id` = \"#id\"",stmt.prefix+stmt.middlefix+stmt.postfix);
    }

    @Test
    public void testInsertStatementCorrect()
    {
        ReservoirEntityDataPresenter depp = getPresenterMock();

        MySqlStatement stmt = MySqlStatement.getInsertValueStatement(depp);
        System.out.println((stmt.prefix+stmt.middlefix+stmt.postfix));
        assertEquals("INSERT INTO `toxitter`.`testreservoir`(`id`, `name`, `powerlevel`)\n" +
                " VALUES (\"#id\", \"Malte\", 9034)", (stmt.prefix+stmt.middlefix+stmt.postfix));
    }

    @Test
    public void testDeleteStatementCorrect()
    {
        ReservoirEntityDataPresenter depp = getPresenterMock();

        MySqlStatement stmt = MySqlStatement.getDeleteValueStatement(depp);
        System.out.println((stmt.prefix+stmt.middlefix+stmt.postfix));
        assertEquals("DELETE FROM `toxitter`.`testreservoir` WHERE `id` = \"#id\"",(stmt.prefix+stmt.middlefix+stmt.postfix));
    }
}
