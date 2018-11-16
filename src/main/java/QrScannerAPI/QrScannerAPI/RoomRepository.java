package QrScannerAPI.QrScannerAPI;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomRepository {

    public void addRoom(RoomModel room) {
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(
                    "insert into QR_CODES (room_number) values (?)");
            preparedStatement.setString(1, room.getRoomNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
