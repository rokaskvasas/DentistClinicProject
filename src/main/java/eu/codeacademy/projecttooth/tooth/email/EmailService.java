package eu.codeacademy.projecttooth.tooth.email;

public interface EmailService {
    void send(String to, String email, String subject);
}
