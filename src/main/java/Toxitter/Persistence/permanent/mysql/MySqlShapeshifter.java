package Toxitter.Persistence.permanent.mysql;

import Toxitter.Persistence.DataAccessToReservoirEntityBeingPersisted;
import Toxitter.Persistence.ReservoirEntityDataPresenter;

public class MySqlShapeshifter
{
    private MySqlTypeTransformer tf;

    public MySqlShapeshifter()
    {
    }

    public MySqlStatement getCreateTableStatement()
    {

        return null;
    }

    public MySqlStatement getInsertValueStatement(
            ReservoirEntityDataPresenter redp,
            Comparable key, Object value)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "INSERT INTO table ("+redp.getTable().tableName()+")\n VALUES (";
        stmt.middlefix = redp.getValueStringSeparatedBy(",", new MySqlTypeTransformer());
        stmt.postfix = ") ON DUPLICATE KEY UPDATE\n";

        return stmt;
    }

    public MySqlStatement getDeleteValueStatement()
    {
        return null;
    }

    public MySqlStatement getUpdateValueStatement()
    {
        return null;
    }
}
