package ru.seals.auth.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(Exception exception) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        p.setType(setTypeFromErrorCode(exception.getClass().getSimpleName()));
        return p;
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(Exception exception) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        p.setType(setTypeFromErrorCode(exception.getClass().getSimpleName()));
        return p;
    }

    private URI setTypeFromErrorCode(String errorCode) {
        String ERROR_URL = "http://localhost:8080/error-code/";
        return new DefaultUriBuilderFactory()
                .uriString(ERROR_URL)
                .pathSegment(errorCode)
                .build();
    }
}
