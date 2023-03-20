package nl.techiteasy.Techiteasy_part2.Controllers;

import jakarta.validation.Valid;
import nl.techiteasy.Techiteasy_part2.Dto.InputDto.IdInputDto;
import nl.techiteasy.Techiteasy_part2.Dto.InputDto.TelevisionInputDto;
import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.TelevisionDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Services.TelevisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RequestMapping("televisions")
@RestController
public class TelevisionController {

    private final TelevisionService service;

    public TelevisionController (TelevisionService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<TelevisionDto>> getAllTelevisions(){
        List<TelevisionDto> televisionOutputDto = service.getAllTelevisions();
        return ResponseEntity.ok(televisionOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelevisionDto> getTelevisionById(@PathVariable Long id) {
        TelevisionDto televisionOutputDto = new TelevisionDto();
        televisionOutputDto = service.getTelevisionById(id);
        return ResponseEntity.ok(televisionOutputDto);
    }

    @PostMapping()
    public ResponseEntity<Object> createTelevision(@Valid @RequestBody TelevisionInputDto teleInDto, BindingResult br) {

        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage() + "\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        TelevisionDto teleOutputDto = service.createTelevision(teleInDto);

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + teleInDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(teleInDto);
    }

    @PutMapping("/{id}")
        public ResponseEntity<TelevisionDto> updateTelevision(@PathVariable Long id, @RequestBody TelevisionInputDto teleInDto) {
        TelevisionDto teleOutputDto = service.updateTelevision(id, teleInDto);
        return ResponseEntity.ok().body(teleOutputDto);
    }

    @PutMapping("/{id}/remoteController")
    public void assignRemoteControllerToTelevision (@PathVariable("id") Long id, @Valid @RequestBody IdInputDto input) {
        service.assignRemoteControllerToTelevision(id, input.id);

    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeTelevision (@PathVariable Long id) {
        boolean isDeleted = service.deleteTelevision(id);
        if (isDeleted) {
            return ResponseEntity.ok().body("Element is deleted");
        } else {
            throw new RecordNotFoundException("No element found with this ID code");
        }
    }

}
