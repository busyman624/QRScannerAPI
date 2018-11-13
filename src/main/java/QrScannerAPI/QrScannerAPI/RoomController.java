package QrScannerAPI.QrScannerAPI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomRepository roomRepository;

    RoomController(){
        roomRepository = new RoomRepository();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRoom() {
        roomRepository.addRoom();
    }
}
