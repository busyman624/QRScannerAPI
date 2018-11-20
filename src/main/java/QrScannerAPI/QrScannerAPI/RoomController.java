package QrScannerAPI.QrScannerAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/ping")
    public ResponseEntity ping(){
        return ResponseEntity.status(HttpURLConnection.HTTP_OK).body(null);
    }

    @PostMapping
    public ResponseEntity<String> addRoom(
            @RequestBody RoomModel room) {
        if(room.getRoomNumber().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("RoomNumber must be filled out");

        String roomNumber = roomRepository.addRoom(room);

        return roomNumber == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room already exists"):
        ResponseEntity.status(HttpStatus.CREATED).body(roomNumber);
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<RoomModel> getRoom(
            @PathVariable("roomNumber") String roomNumber){
        if(roomNumber.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        RoomModel room = roomRepository.getRoom(roomNumber);

        return room == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) :
                ResponseEntity.status(HttpStatus.OK).body(room);
    }

    @PostMapping("/{roomNumber}/qrCode")
    public ResponseEntity<String> addQrCode(
            @PathVariable("roomNumber") String roomNumber,
            @RequestParam("qrCode")MultipartFile qrCode){
        if (qrCode.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");

        try {
            byte[] bytes = qrCode.getBytes();
            boolean wasAdded = roomRepository.addQrCode(roomNumber, bytes);

            return wasAdded ? ResponseEntity.status(HttpStatus.OK).body(null) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot parse file");
        }
    }

    @GetMapping("/{roomNumber}/qrCode")
    public ResponseEntity<byte[]> getQrCode(
            @PathVariable("roomNumber") String roomNumber){
        byte[] qrCode = roomRepository.getQrCode(roomNumber);
        if(qrCode == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(qrCode.length);
        return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
    }

    @PostMapping("/{roomNumber}/map")
    public ResponseEntity<String> addMap(
            @PathVariable("roomNumber") String roomNumber,
            @RequestParam("map")MultipartFile map){
        if (map.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");

        try {
            byte[] bytes = map.getBytes();
            boolean wasAdded = roomRepository.addMap(roomNumber, bytes);

            return wasAdded ? ResponseEntity.status(HttpStatus.OK).body(null) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot parse file");
        }
    }

    @GetMapping("/{roomNumber}/map")
    public ResponseEntity<byte[]> getMap(
            @PathVariable("roomNumber") String roomNumber){
        byte[] map = roomRepository.getMap(roomNumber);
        if(map == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(map.length);
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }
}
