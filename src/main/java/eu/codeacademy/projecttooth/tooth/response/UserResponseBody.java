package eu.codeacademy.projecttooth.tooth.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@RequiredArgsConstructor
public class UserResponseBody {
    private final Long userId;
    private final String email;
    private final Collection<? extends GrantedAuthority> roles;
}
