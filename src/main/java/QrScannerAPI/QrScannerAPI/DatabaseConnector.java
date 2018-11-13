package QrScannerAPI.QrScannerAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection connection;

    private final String url="jdbc:sqlite:D:/pg/repos/K/QRScanner/db/QRScanner.db";

    public void CreateConnection(){

        connection = null;

        try {
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
