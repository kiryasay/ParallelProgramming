package kiryasay.springmvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelInsert {
    String url;
    String user;
    String password;

    public ParallelInsert()
    {
        this.url = "jdbc:postgresql://localhost:5432/ParalelDB";
        this.user= "postgres";
        this.password = "kiryasay";
    }

    public void insertInto()
    {
        long start = System.currentTimeMillis();
        try(Connection connection = DriverManager.getConnection(url, user, password)){

            ExecutorService executor = Executors.newFixedThreadPool(5);

            String sql = "INSERT INTO client (number, name) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for(int i = 0; i < 1000; i++){
                executor.submit(() -> {
                    try{
                        Random random = new Random();
                        int randomValue = random.nextInt(100);

                        preparedStatement.setInt(1, randomValue);
                        preparedStatement.setString(2, "Kirill");

                        preparedStatement.executeUpdate();


                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                });
            }
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Время выполнения = " + (end-start) + "миллисекнуд");
    }
}
