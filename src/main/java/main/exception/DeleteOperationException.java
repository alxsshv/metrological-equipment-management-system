package main.exception;

public class DeleteOperationException extends RuntimeException{
    public DeleteOperationException(String message) {
        super(message);
    }
}
