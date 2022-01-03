package eu.codeacademy.projecttooth.tooth.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
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
        http.authorizeRequests().antMatchers("/api/doctors").permitAll().anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and().httpBasic();
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
