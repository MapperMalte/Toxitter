package Toxitter.Core.Elemental.earth.persistence.mysql;

import Toxitter.Core.Elemental.earth.ID;
import Toxitter.Core.Elemental.earth.ReservoirEntity;
import Toxitter.Core.Elemental.water.DataAccessToReservoirEntity;
import Toxitter.Core.Elemental.water.ReservoirEntityDataPresenter;
import Toxitter.Core.Elemental.wind.DiamondList;

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
            ReservoirEntity c)
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
                    } else if ( field.type.equals(ID.class) )
                    {
                        System.out.println("idResult "+resultSet.getString(field.fieldName));
                        dataAccessToReservoirEntity.set(field.fieldName,resultSet.getString(field.fieldName));
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
