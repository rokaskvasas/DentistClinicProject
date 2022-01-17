package eu.codeacademy.projecttooth.tooth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.codeacademy.projecttooth.tooth.response.UserLoginResponseBody;
import eu.codeacademy.projecttooth.tooth.response.UserLoginResponseObject;
import eu.codeacademy.projecttooth.tooth.security.JWTUtility;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtility jwtUtility;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON_VALUE);
        super.unsuccessfulAuthentication(request, response, failed);
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Authentication Failed");
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication
    ) throws IOException, ServletException {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        String access_token = jwtUtility.accessTokenForPrincipal(user, request);
        String refresh_token = jwtUtility.refreshTokenForPrincipal(user, request);
        UserLoginResponseBody responseBody = new UserLoginResponseBody(
                user.getUserId(),
                user.getUsername(),
                user.getAuthorities()
        );

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);


        UserLoginResponseObject responseObject = new UserLoginResponseObject(responseBody, tokens);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseObject);
    }
}
