package eu.codeacademy.projecttooth.tooth.exception;

public class DuplicateServiceException extends RuntimeException{
    public DuplicateServiceException(String msg) {
        super(msg);
    }
}
