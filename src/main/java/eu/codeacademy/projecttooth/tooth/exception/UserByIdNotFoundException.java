package eu.codeacademy.projecttooth.tooth.exception;

public class UserByIdNotFoundException extends RuntimeException {
    public UserByIdNotFoundException(String msg) {super(msg);
    }
}
