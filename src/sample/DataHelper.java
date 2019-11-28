package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataHelper {
    private static String url = "jdbc:postgresql://localhost:5432/mercubuana";
    private static String username = "postgres";
    private static String password = "foxtrot21";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed, cause = " + e.getMessage());
        }

        return connection;
    }
}
