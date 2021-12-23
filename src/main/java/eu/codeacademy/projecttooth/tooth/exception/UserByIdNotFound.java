package eu.codeacademy.projecttooth.tooth.exception;

public class UserByIdNotFound extends RuntimeException {
    public UserByIdNotFound(String msg) {super(msg);
    }
}
