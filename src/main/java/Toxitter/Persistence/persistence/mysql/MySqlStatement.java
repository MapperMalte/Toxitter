package Toxitter.Persistence.persistence.mysql;

import Toxitter.Persistence.ReservoirEntityDataPresenter;

import java.security.Key;

public class MySqlStatement
{
    public String prefix;
    public String middlefix;
    public String postfix;

    private MySqlTypeTransformer tf;

    public static MySqlStatement getCreateTableStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "CREATE TABLE "
                + redp.escape("toxitter") +
                "."
                +redp.escape(redp.getTable().tableName())+" (";

        stmt.middlefix = redp.getVariableRepresentationStringSeparatedBy(", ", new MySqlTypeTransformer());

        stmt.postfix = ", PRIMARY KEY ("+redp.escape(redp.getTable().primaryKey())+")" + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
        System.out.println(stmt.prefix+stmt.middlefix+stmt.postfix);
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

        return stmt;
    }

    public static MySqlStatement getSelectStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();
        stmt.prefix = "SELECT * from "+
                redp.escape("toxitter")+"."+
                redp.escape(redp.getTable().tableName());
        stmt.middlefix = " WHERE "+
                redp.escape(redp.getTable().primaryKey())+
                " = "+
                redp.escapeValue(redp.getTable().primaryKey(),
                redp.getAccess().get(redp.getTable().primaryKey()).toString(),
                        new MySqlTypeTransformer());
        stmt.postfix = "";

        return stmt;
    }

    public static MySqlStatement getUpdateValueStatement(ReservoirEntityDataPresenter redp)
    {
        MySqlStatement stmt = new MySqlStatement();

        stmt.prefix = "UPDATE "+
                redp.escape("toxitter")+
                "."+
                redp.escape(redp.getTable().tableName());
        stmt.middlefix = " SET "+redp.getAssignmentSeparatedBy(", ",new MySqlTypeTransformer());
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
        return stmt;
    }
}
