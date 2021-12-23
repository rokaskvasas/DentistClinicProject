package eu.codeacademy.projecttooth.tooth.security;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.exception.EmailNotFoundException;
import eu.codeacademy.projecttooth.tooth.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


        private final CustomUserDetailsService customUserDetailsService;
        private final PasswordService passwordService;
//    private final UserDetailsService userDetailsService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Bean
//    public UserDetailsService userDetailsService(UserEntityRepository userEntityRepository){
//        return email -> userEntityRepository.findByEmail(email).map(this::convertUserEntityToPrincipal)
//                .orElseThrow(() -> new EmailNotFoundException("Email not found"+email));
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/api/doctors").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordService.passwordEncoder());
    }

//    private UserPrincipal convertUserEntityToPrincipal(UserEntity user){
//        return new UserPrincipal(user.getUserId(),user.getEmail(),user.getPassword(),user.getRole());
//    }
}
