package nl.techiteasy.Techiteasy_part2.Controllers;

import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.TelevisionOutputDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.ObjectNameTooLongException;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Model.Television;
import nl.techiteasy.Techiteasy_part2.Services.TelevisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Television> createTelevision(@RequestBody Television television) {
        if (television.name.length() < 30) {
            System.out.println(television.name.length());
            televisions.add(television);
            return new ResponseEntity<>(television, HttpStatus.CREATED);
        }
        else {
            throw new ObjectNameTooLongException("make it under 30 characters");
        }
    }

    @PutMapping("/{id}")
        public ResponseEntity<Television> updateTelevision(@PathVariable int id, @RequestBody Television television) {
        if (id >=0 && id < televisions.size()) {
            televisions.set(id, television);
            return new ResponseEntity<>(television, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeTelevision (@PathVariable int id) {
        if (id >=0 && id < televisions.size()) {
            televisions.remove(id);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
