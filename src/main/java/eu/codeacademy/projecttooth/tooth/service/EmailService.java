package eu.codeacademy.projecttooth.tooth.service;

public interface EmailService {
    void send(String to, String emailText, String subject);
}
