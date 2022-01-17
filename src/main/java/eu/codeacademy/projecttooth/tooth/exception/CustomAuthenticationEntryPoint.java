package eu.codeacademy.projecttooth.tooth.exception;

import eu.codeacademy.projecttooth.tooth.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ErrorResponse errorResponse = new ErrorResponse("Message: " + authException.getMessage());

        try {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(errorResponse.getMessage());

        } catch (Exception e1) {
            log.error("Exception in AuthenticationEntryPoint");
            e1.getMessage();
            e1.printStackTrace();
        }
    }
}
