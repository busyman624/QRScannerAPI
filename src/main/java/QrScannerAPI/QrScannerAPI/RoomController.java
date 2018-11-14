package QrScannerAPI.QrScannerAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomRepository roomRepository;

    RoomController(){

        roomRepository = new RoomRepository();
    }

    @PostMapping(value = "/add")
    public ResponseEntity addRoom(@RequestBody RoomModel room) {
        if(room.getMessage()=="")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        roomRepository.addRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
