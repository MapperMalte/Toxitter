package Toxitter.Persistence.persistence.mysql;

import Toxitter.Persistence.ReservoirEntityDataPresenter;

public class MySqlStatement
{
    public String prefix;
    public String middlefix;
    public String postfix;

    private MySqlTypeTransformer tf;

    public static MySqlStatement getCreateTableStatement()
    {

        return null;
    }

    public static MySqlStatement getInsertValueStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "INSERT INTO "+redp.getTable().tableName()+"("+redp.getFieldStringSeparatedBy(", ", new MySqlTypeTransformer())+")\n VALUES (";
        stmt.middlefix = redp.getValueStringSeparatedBy(", ", new MySqlTypeTransformer());
        stmt.postfix = ")";
        return stmt;
    }

    public static MySqlStatement getDeleteValueStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "DELETE FROM "+redp.getTable().tableName();
        stmt.middlefix = "";
        stmt.postfix = " WHERE "+redp.getTable().primaryKey() + " = '" + redp.getAccess().get(redp.getTable().primaryKey())+"'";

        return stmt;
    }

    public static MySqlStatement getUpdateValueStatement()
    {
        return null;
    }
}
