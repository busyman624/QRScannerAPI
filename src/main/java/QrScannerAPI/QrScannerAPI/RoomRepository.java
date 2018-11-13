package QrScannerAPI.QrScannerAPI;

import java.sql.SQLException;
import java.sql.Statement;

public class RoomRepository {

    public void addRoom() {
        try {
            Statement statement = DatabaseConnector.connection.createStatement();
            statement.execute("insert into QR_CODES (message) values ('new cool message')");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
