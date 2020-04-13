package server.exceptions;

public class IncorrectUserDataException extends RuntimeException {
    public IncorrectUserDataException() {
        super("Users data is incorrect");
    }
}
