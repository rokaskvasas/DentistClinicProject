package eu.codeacademy.projecttooth.tooth.exception;

public class DoctorByIdNotFoundException extends RuntimeException {
    public DoctorByIdNotFoundException(String msg) {
        super(msg);
    }
}
