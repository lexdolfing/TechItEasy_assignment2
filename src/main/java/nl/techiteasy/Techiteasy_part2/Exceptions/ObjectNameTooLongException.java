package nl.techiteasy.Techiteasy_part2.Exceptions;

public class ObjectNameTooLongException extends RuntimeException{

    public ObjectNameTooLongException() {
        super();
    }

    public ObjectNameTooLongException(String message) {
        super(message);
    }
}
