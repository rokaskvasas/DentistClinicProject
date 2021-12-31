package eu.codeacademy.projecttooth.tooth.security;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.exception.EmailNotFoundException;
import eu.codeacademy.projecttooth.tooth.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userEntityRepository.findByEmail(email).map(this::convertUserEntityToPrincipal)
                .orElseThrow(() -> new EmailNotFoundException("Email not found"+email));
    }
    private UserPrincipal convertUserEntityToPrincipal(UserEntity user){
        return new UserPrincipal(user.getUserId(),user.getEmail(),user.getPassword(),user.getRole());
    }
}