package kiryasay.springmvc;

import java.sql.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Parallel {
    String url;
    String user;
    String password;

    public Parallel() {
        this.url = "jdbc:postgresql://localhost:5432/ParalelDB";
        this.user = "postgres";
        this.password = "kiryasay";
    }

    public void insertInto(int number) {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(10);


            DBWorker.createClientTable();

            String sql = "INSERT INTO client (name, phone, address, account_number) VALUES (?, ?, ?, ?)";
            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    try (Connection connection = DriverManager.getConnection(url, user, password);
                         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                        for (int j = 0; j < number / 10; j++) {
                            preparedStatement.setString(1, Generator.generateName());
                            preparedStatement.setString(2, Generator.generatePhoneNumber());
                            preparedStatement.setString(3, Generator.generateAddress());
                            preparedStatement.setString(4, Generator.generateAccountNumber());
                            preparedStatement.executeUpdate();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();
        System.out.println("| Параллельное заполнение выполнилось за " + (end-start) + " миллисекнуд");
        }



    public void selectWhere(String city, int number) throws InterruptedException {
        long start = System.currentTimeMillis();
        int count = 0;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        String select = "UPDATE client SET address = 'Изменено'" +
                "WHERE id = (\n" +
                "    SELECT id FROM client\n" +
                "    WHERE address LIKE " + "'%"+city+"%'\n" +
                "    LIMIT 1\n" +
                ");";


           for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    try (Connection connection = DriverManager.getConnection(url, user, password);
                         PreparedStatement preparedStatement = connection.prepareStatement(select)) {

                        for (int j = 0; j < number / 10; j++) {
                            preparedStatement.executeUpdate();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

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
        System.out.println("| Параллельная выборка выполнилось за " + (end-start) + " миллисекнуд");
        System.out.println("| Количетсво записей содержащих в себе " + "'" +city +"'" + " равно " + count);
        }

    }

