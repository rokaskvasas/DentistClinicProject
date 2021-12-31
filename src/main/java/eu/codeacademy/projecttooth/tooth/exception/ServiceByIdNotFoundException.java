package eu.codeacademy.projecttooth.tooth.exception;

public class ServiceByIdNotFoundException extends RuntimeException {
    public ServiceByIdNotFoundException(String msg) {
        super(msg);
    }
}
