package Toxitter.Persistence.persistence.mysql;

import Toxitter.Model.ReservoirEntity;
import Toxitter.Persistence.DataAccessToReservoirEntity;
import Toxitter.Persistence.ReservoirEntityDataPresenter;
import theory.DiamondList;

import java.sql.*;

public class MySql
{
    public static void create()
    {
        MySqlStatement stmt = MySqlStatement.getCreateDatabaseStatement();

        try {
            System.out.println("Now executing stmt : "+stmt.toString());
            Connection myCon = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/toxitter",
                    "toxitter",
                    "secret");

            Statement statement = myCon.createStatement();
            statement.execute(stmt.toString());
            System.out.println("Success");
            myCon.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void selectIntoReservoirEntity(
            ReservoirEntity<? extends Comparable<?>> c)
    {
        DataAccessToReservoirEntity dataAccessToReservoirEntity = new DataAccessToReservoirEntity(c);
        MySqlStatement stmt = MySqlStatement.getSelectStatement(new ReservoirEntityDataPresenter(dataAccessToReservoirEntity));

        try {
            System.out.println("Now executing stmt : "+stmt.toString());
            Connection myCon = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/toxitter",
                    "toxitter",
                    "secret");

            Statement statement = myCon.createStatement();

            System.out.println("Read Data");
            ResultSet resultSet = statement.executeQuery(stmt.toString());
            System.out.println("Success!");
            while ( resultSet.next() )
            {
                System.out.println("next");
                DiamondList<DataAccessToReservoirEntity.DataAccessField> data = dataAccessToReservoirEntity.getAllFields();
                data.bottom();
                while(!data.isPointerNull())
                {
                    DataAccessToReservoirEntity.DataAccessField field = data.getCurrent();
                    System.out.println("Field: "+field.fieldName+" with type "+field.type.toString());

                    if ( field.type.equals(String.class) )
                    {
                        System.out.println("strResult "+resultSet.getString(field.fieldName));
                        dataAccessToReservoirEntity.set(field.fieldName,resultSet.getString(field.fieldName));
                    } else if ( field.type.toString().equals("int") )
                    {
                        System.out.println("intResult "+resultSet.getInt(field.fieldName));
                        dataAccessToReservoirEntity.set(field.fieldName,resultSet.getInt(field.fieldName));
                    }
                    data.next();
                }

                System.out.println("Result: "+resultSet.toString());
            }

            myCon.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void execute(MySqlStatement stmt)
    {
        try {
            System.out.println("Now executing stmt : "+stmt.toString());
            Connection myCon = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/toxitter",
                    "toxitter",
                    "secret");

            Statement statement = myCon.createStatement();

            if ( stmt.type == MySqlStatement.STATEMENT_TYPE_READ_DATA )
            {
                System.out.println("Read Data");
                ResultSet resultSet = statement.executeQuery(stmt.toString());
                System.out.println("Success!");
                while ( resultSet.next() )
                {
                    System.out.println("Result: "+resultSet.toString());
                }
            } else if ( stmt.type == MySqlStatement.STATEMENT_TYPE_INSERT_OR_UPDATE_DATA ||
                        stmt.type == MySqlStatement.STATEMENT_TYPE_CREATE_TABLE_OR_DATABASE )
            {
                System.out.println("Insert Data");
                statement.execute(stmt.toString());
                System.out.println("Success!");
            }

            myCon.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
