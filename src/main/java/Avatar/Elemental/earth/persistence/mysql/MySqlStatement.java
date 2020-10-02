package Avatar.Elemental.earth.persistence.mysql;

import Avatar.Elemental.water.ReservoirEntityDataPresenter;

public class MySqlStatement
{
    public static final int STATEMENT_TYPE_CREATE_TABLE_OR_DATABASE = 0;
    public static final int STATEMENT_TYPE_INSERT_OR_UPDATE_DATA = 1;
    public static final int STATEMENT_TYPE_READ_DATA = 2;
    public static final int STATEMENT_TYPE_DELETE_DATA = 3;

    public String prefix;
    public String middlefix;
    public String postfix;
    public int type;

    private MySqlTypeTransformer tf;

    public static MySqlStatement getCreateTableStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "CREATE TABLE "
                + redp.escape("toxitter") +
                "."
                +redp.escape(redp.getTable().tableName())+" (";

        stmt.middlefix = redp.getFieldsSeparatedBy(", ", new MySqlTypeTransformer());
        stmt.postfix = ", PRIMARY KEY ("+redp.escape(redp.getTable().primaryKey())+")" + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
        System.out.println(stmt.prefix+stmt.middlefix+stmt.postfix);
        stmt.type = MySqlStatement.STATEMENT_TYPE_CREATE_TABLE_OR_DATABASE;
        return stmt;
    }

    public static MySqlStatement getInsertValueStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "INSERT INTO "+
                redp.escape("toxitter")
                +"."+
                redp.escape(redp.getTable().tableName())
                    +"("
                    +redp.getFieldStringSeparatedBy(", ", new MySqlTypeTransformer())
                    +")\n VALUES (";

        stmt.middlefix = redp.getValueStringSeparatedBy(", ", new MySqlTypeTransformer());
        stmt.postfix = ")";
        stmt.type = MySqlStatement.STATEMENT_TYPE_INSERT_OR_UPDATE_DATA;
        return stmt;
    }

    public static MySqlStatement getDeleteValueStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "DELETE FROM "+
                redp.escape("toxitter")+"."+
                redp.escape(redp.getTable().tableName());
        stmt.middlefix = "";
        stmt.postfix = " WHERE "+redp.escape(redp.getTable().primaryKey()) + " = " +
                redp.escapeValue(redp.getTable().primaryKey(),
                        redp.getAccess().get(redp.getTable().primaryKey()).toString(),
        new MySqlTypeTransformer());
        stmt.type = MySqlStatement.STATEMENT_TYPE_DELETE_DATA;

        return stmt;
    }

    public static MySqlStatement getSelectStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "SELECT * from " +
                redp.escape("toxitter")+"." +
                redp.escape(redp.getTable().tableName());
        stmt.middlefix = " WHERE " +
                redp.escape(redp.getTable().primaryKey()) +
                " = " +
                redp.escapeValue
                (
                    redp.getTable().primaryKey(),
                    redp.getAccess().get(redp.getTable().primaryKey()).toString(),
                    new MySqlTypeTransformer()
                );
        stmt.postfix = "";
        stmt.type = MySqlStatement.STATEMENT_TYPE_READ_DATA;

        return stmt;
    }

    public static MySqlStatement getUpdateValueStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();

        stmt.prefix = "UPDATE "+
                redp.escape("toxitter")+
                "."+
                redp.escape(redp.getTable().tableName());
        stmt.middlefix = " SET "+redp.getFieldEqualsValueSeparatedBy(", ",new MySqlTypeTransformer());
        /*
        UPDATE toxitter.tableName
        SET
            column_name1 = expr1,
            column_name2 = expr2,
            ...
        [WHERE
            condition];
         */
        stmt.postfix = " WHERE " + redp.escape(redp.getTable().primaryKey())+
                " = "+
                redp.escapeValue(redp.getTable().primaryKey(),
                        redp.getAccess().get(redp.getTable().primaryKey()).toString(),
                        new MySqlTypeTransformer());
        stmt.type = MySqlStatement.STATEMENT_TYPE_INSERT_OR_UPDATE_DATA;

        return stmt;
    }

    public static MySqlStatement getCreateDatabaseStatement()
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "CREATE DATABASE toxitter";
        stmt.middlefix = "";
        stmt.postfix = "";
        stmt.type = MySqlStatement.STATEMENT_TYPE_CREATE_TABLE_OR_DATABASE;

        return  stmt;
    }

    @Override
    public String toString()
    {
        return this.prefix+this.middlefix+this.postfix;
    }
}
