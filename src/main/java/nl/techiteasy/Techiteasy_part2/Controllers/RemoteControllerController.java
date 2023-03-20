package nl.techiteasy.Techiteasy_part2.Controllers;

import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.RemoteControllerDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Services.RemoteControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("remotecontroller")
public class RemoteControllerController {
    private final RemoteControllerService remoteControllerService;

    public RemoteControllerController(RemoteControllerService remoteControllerService) {
        this.remoteControllerService = remoteControllerService;
    }

    @GetMapping()
    public ResponseEntity<List<RemoteControllerDto>> getAllremoteControllers() {
        List<RemoteControllerDto> remoteControllerDto = remoteControllerService.getAllRemoteControllers();
        return ResponseEntity.ok(remoteControllerDto);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<RemoteControllerDto> getRemoteControllerById(@PathVariable Long id) {
        RemoteControllerDto remoteControllerDto = remoteControllerService.getRemoteControllerById(id);
        return ResponseEntity.ok(remoteControllerDto);
    }

    @PostMapping()
    public ResponseEntity<RemoteControllerDto> createRemoteController (@RequestBody RemoteControllerDto dto) {
        RemoteControllerDto remoteControllerDto = remoteControllerService.createRemoteController(dto);
        return ResponseEntity.ok(remoteControllerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeRemoteController (@PathVariable Long id) {
        boolean isDeleted = remoteControllerService.deleteRemoteController(id);
        if(isDeleted) {
            return ResponseEntity.ok().body("Element is deleted");
        } else {
            throw new RecordNotFoundException("No element found with this ID code");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RemoteControllerDto> updateRemoteController(@PathVariable Long id, @RequestBody RemoteControllerDto dto) {
        RemoteControllerDto remoteControllerDto = remoteControllerService.updateRemoteController(id, dto);
        return ResponseEntity.ok().body(remoteControllerDto);
    }

}
