package nl.techiteasy.Techiteasy_part2.Controllers;

import jakarta.validation.Valid;
import nl.techiteasy.Techiteasy_part2.Dto.InputDto.TelevisionInputDto;
import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.TelevisionOutputDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.ObjectNameTooLongException;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Model.Television;
import nl.techiteasy.Techiteasy_part2.Services.TelevisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
@RequestMapping("television")
@RestController
public class TelevisionController {
    private List<Television> televisions = new ArrayList<>();

    private final TelevisionService service;

    public TelevisionController (TelevisionService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<TelevisionOutputDto>> getAllTelevisions(){
        List<TelevisionOutputDto> televisionOutputDto = service.getAllTelevisions();
        return ResponseEntity.ok(televisionOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelevisionOutputDto> getTelevisionById(@PathVariable Long id) {
        TelevisionOutputDto televisionOutputDto = new TelevisionOutputDto();
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

        TelevisionOutputDto teleOutputDto = service.createTelevision(teleInDto);

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + teleInDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(teleInDto);
    }

    @PutMapping("/{id}")
        public ResponseEntity<TelevisionOutputDto> updateTelevision(@PathVariable Long id, @RequestBody TelevisionInputDto teleInDto) {
        TelevisionOutputDto teleOutputDto = service.updateTelevision(id, teleInDto);
        return ResponseEntity.ok().body(teleOutputDto);
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
