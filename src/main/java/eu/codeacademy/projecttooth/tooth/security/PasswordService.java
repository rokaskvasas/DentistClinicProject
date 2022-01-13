package eu.codeacademy.projecttooth.tooth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordService  {

  private final PasswordEncoder passwordEncoder;

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
