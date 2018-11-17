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

    @PostMapping(value = "/add")
    public ResponseEntity<Integer> addRoom(@RequestBody RoomModel room) {
        if(room.getRoomNumber().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        int roomId = roomRepository.addRoom(room);
        if(roomId == -1)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomId);
    }

    @PostMapping("/{roomId}/qrCode")
    public ResponseEntity<String> addQrCode(
            @PathVariable("roomId") int roomId,
            @RequestParam("qrCode")MultipartFile qrCode
            ){
        if (!qrCode.isEmpty()) {
            try {
                byte[] bytes = qrCode.getBytes();
                roomRepository.addQrCode(roomId, bytes);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot parse file");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{roomId}/qrCode")
    public ResponseEntity<byte[]> getQrCode(
            @PathVariable("roomId") int roomId
    ){
        byte[] qrCode = roomRepository.getQrCode(roomId);
        if(qrCode == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(qrCode.length);
        return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
    }
}
