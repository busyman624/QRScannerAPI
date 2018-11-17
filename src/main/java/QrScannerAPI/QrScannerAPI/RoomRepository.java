package QrScannerAPI.QrScannerAPI;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomRepository {

    public int addRoom(RoomModel room) {
        try {
            PreparedStatement addStatement = DatabaseConnector.connection.prepareStatement(
                    "insert into ADDED_ROOMS" +
                        " (ROOM_NUMBER, TYPE, AVAILABILITY, DESCRIPTION)" +
                        " values (?, ?, ? , ?)");
            addStatement.setString(1, room.getRoomNumber());
            addStatement.setString(2, room.getType());
            addStatement.setString(3, room.getAvailability());
            addStatement.setString(4, room.getDescription());
            addStatement.execute();

            PreparedStatement getIdStatement = DatabaseConnector.connection.prepareStatement(
                    "SELECT ROOM_ID FROM ADDED_ROOMS WHERE ROOM_NUMBER=(?) order by ROWID desc");
            getIdStatement.setString(1, room.getRoomNumber());
            ResultSet resultSet = getIdStatement.executeQuery();
            return resultSet.getInt("ROOM_ID");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public void addQrCode(int roomId, byte[] qrCode){
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(
                    "update ADDED_ROOMS set QR_CODE = (?) where ROOM_ID = (?)");
            preparedStatement.setBytes(1, qrCode);
            preparedStatement.setInt(2, roomId);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public byte[] getQrCode(int roomId){
        try {
            PreparedStatement preparedStatement = DatabaseConnector.connection.prepareStatement(
                    "SELECT QR_CODE FROM ADDED_ROOMS WHERE ROOM_ID=(?)");
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return IOUtils.toByteArray(resultSet.getBinaryStream("DATA_BLOB"));
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
