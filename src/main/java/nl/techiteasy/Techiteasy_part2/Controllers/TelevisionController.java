package nl.techiteasy.Techiteasy_part2.Controllers;

import nl.techiteasy.Techiteasy_part2.Exceptions.ObjectNameTooLongException;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Model.Television;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TelevisionController {
    private List<Television> televisions = new ArrayList<>();

    @GetMapping("televisions")
    public ResponseEntity<List<Television>> getTelevisions(){
        return new ResponseEntity<List<Television>>(televisions, HttpStatus.OK);
    }

    @GetMapping("televisions/{id}")
    public ResponseEntity<Television> getOneTelevision(@PathVariable int id) {
        if (id >= 0 && id < televisions.size()) {
            return new ResponseEntity<>(televisions.get(id),HttpStatus.OK);
        }
        throw new RecordNotFoundException("ID not available");
    }

    @PostMapping("televisions")
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

    @PutMapping("televisions/{id}")
        public ResponseEntity<Television> updateTelevision(@PathVariable int id, @RequestBody Television television) {
        if (id >=0 && id < televisions.size()) {
            televisions.set(id, television);
            return new ResponseEntity<>(television, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("televisions/{id}")
    public ResponseEntity<Object> removeTelevision (@PathVariable int id) {
        if (id >=0 && id < televisions.size()) {
            televisions.remove(id);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
