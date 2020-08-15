package Toxitter.Persistence.persistence.mysql.test;

import org.junit.Test;

import java.sql.*;

public class MySqlConnectionTest
{
    @Test
    public void testMysqlConnection()
    {
        try {
            Connection myCon = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/toxitter",
                    "toxitter",
                    "secret");

            Statement statement = myCon.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user");

            while ( resultSet.next() )
            {
                System.out.println("userId: "+resultSet.getString("userId")+", name: "+resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
