package eu.codeacademy.projecttooth.tooth.exception;

import eu.codeacademy.projecttooth.tooth.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFound(ObjectNotFoundException e) {
        log.error("Object not found", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ErrorResponse emailNotFound(EmailNotFoundException e) {
        log.error("Email not found", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(IncorrectTimeException.class)
    public ErrorResponse incorrectAvailabilityTime(IncorrectTimeException e) {
        log.error("Incorrect availability time", e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(QualificationException.class)
    public ErrorResponse minimumQualification(QualificationException e) {
        log.error("minimum qualification ", e);
        return new ErrorResponse(e.getMessage());
    }

}
