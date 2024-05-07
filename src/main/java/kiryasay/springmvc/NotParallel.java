package kiryasay.springmvc;

import java.sql.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotParallel {
    String url;
    String user;
    String password;

    public NotParallel()
    {
        this.url = "jdbc:postgresql://localhost:5432/ParalelDB";
        this.user= "postgres";
        this.password = "kiryasay";
    }
    public void insertInto(int number)
    {
        long start = System.currentTimeMillis();
        try(Connection connection = DriverManager.getConnection(url, user, password)){

            DBWorker.createClientTable();

            String sql = "INSERT INTO client (name, phone, address, account_number) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < number; i++)
            {

                preparedStatement.setString(1, Generator.generateName());
                preparedStatement.setString(2, Generator.generatePhoneNumber());
                preparedStatement.setString(3, Generator.generateAddress());
                preparedStatement.setString(4, Generator.generateAccountNumber());

                preparedStatement.executeUpdate();
            }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("| Непараллельное заполнение выполнилось за " + (end-start) + " миллисекнуд");

    }

    public void selectWhere(String city,int number)
    {
        int count = 0;
        long start = System.currentTimeMillis();
        try(Connection connection = DriverManager.getConnection(url, user, password)){

            String select = "UPDATE client SET address = 'Изменено'" +
                    "WHERE id = (\n" +
                    "    SELECT id FROM client\n" +
                    "    WHERE address LIKE " + "'%"+city+"%'\n" +
                    "    LIMIT 1\n" +
                    ");";

            PreparedStatement preparedStatement = connection.prepareStatement(select);

            for (int i = 0; i < number; i++)
            {
                preparedStatement.executeUpdate();

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "SELECT COUNT(*) AS count FROM client WHERE address = 'Изменено'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                count = resultSet.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBWorker.deleteClientTable();
        System.out.println("| Непараллельная выборка выполнилось за " + (end-start) + " миллисекнуд");
        System.out.println("| Количетсво записей содержащих в себе " + "'" +city +"'" + " равно " + count  );


    }
}
