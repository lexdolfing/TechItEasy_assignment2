package nl.techiteasy.Techiteasy_part2.Controllers;

import nl.techiteasy.Techiteasy_part2.Exceptions.ObjectNameTooLongException;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {
        return new ResponseEntity<>("An error occurred: " + exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ObjectNameTooLongException.class)
    public ResponseEntity<Object> elementNameTooLongException(ObjectNameTooLongException exception) {
        return new ResponseEntity<>("Television name is too long:  " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
