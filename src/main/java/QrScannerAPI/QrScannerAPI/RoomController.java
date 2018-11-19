package QrScannerAPI.QrScannerAPI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomRepository roomRepository;

    RoomController(){
        roomRepository = new RoomRepository();
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
}
