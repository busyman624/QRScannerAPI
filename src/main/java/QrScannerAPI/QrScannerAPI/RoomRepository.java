package QrScannerAPI.QrScannerAPI;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoomRepository {

    @Autowired
    Connection connection;

    private boolean roomExists(String roomNumber){
        try{
        PreparedStatement getStatement = connection.prepareStatement(
                "SELECT ROOM_NUMBER FROM ADDED_ROOMS WHERE ROOM_NUMBER=(?)");
        getStatement.setString(1, roomNumber);
        ResultSet resultSet = getStatement.executeQuery();
        if(resultSet.next())
            return true;

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String addRoom(RoomModel room) {
        try {
            if(roomExists(room.getRoomNumber()))
                return null;

            PreparedStatement addStatement = connection.prepareStatement(
                    "insert into ADDED_ROOMS" +
                        " (ROOM_NUMBER, TYPE, AVAILABILITY, DESCRIPTION)" +
                        " values (?, ?, ?, ?)");
            addStatement.setString(1, room.getRoomNumber());
            addStatement.setString(2, room.getType());
            addStatement.setString(3, room.getAvailability());
            addStatement.setString(4, room.getDescription());
            addStatement.execute();

            return room.getRoomNumber();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public RoomModel getRoom(String roomNumber){
        RoomModel room = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT ROOM_NUMBER, TYPE, AVAILABILITY, DESCRIPTION " +
                            "FROM ADDED_ROOMS where ROOM_NUMBER=(?);");
            preparedStatement.setString(1, roomNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                room = new RoomModel(resultSet.getString("ROOM_NUMBER"), resultSet.getString("TYPE"),
                        resultSet.getString("AVAILABILITY"), resultSet.getString("DESCRIPTION"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return room;
    }

    public boolean addQrCode(String roomNumber, byte[] qrCode){
        try {
            if(!roomExists(roomNumber))
                return false;

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update ADDED_ROOMS set QR_CODE = (?) where ROOM_NUMBER = (?)");
            preparedStatement.setBytes(1, qrCode);
            preparedStatement.setString(2, roomNumber);
            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public byte[] getQrCode(String roomNumber){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT QR_CODE FROM ADDED_ROOMS WHERE ROOM_NUMBER=(?)");
            preparedStatement.setString(1, roomNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            return IOUtils.toByteArray(resultSet.getBinaryStream("QR_CODE"));
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
