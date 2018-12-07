package QrScannerAPI.QrScannerAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//klasa odpowiadajaca za przechowywanie polaczenia z baza danych
@Component
public class DatabaseConnector {

    //URL do bazy danych zaciagniete z application.properties
    @Value("${database.url}")
    private String databaseUrl;

    //metoda tworzaca polaczenie z baza danych
    @Bean
    public Connection CreateConnection(){
        Connection connection=null;

        try {
            connection = DriverManager.getConnection(databaseUrl);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
