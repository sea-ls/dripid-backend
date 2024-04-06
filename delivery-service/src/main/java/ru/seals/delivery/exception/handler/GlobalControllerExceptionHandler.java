package ru.seals.delivery.exception.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.seals.delivery.exception.DefaultMessageNotFoundException;
import ru.seals.delivery.exception.MessageTypeNotFoundException;
import ru.seals.delivery.exception.WarehouseNotFoundException;

import java.net.URI;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @Value("${spring.application.name}")
    private String domain;
    @ResponseBody
    @ExceptionHandler(value = {
            DefaultMessageNotFoundException.class,
            MessageTypeNotFoundException.class,
            WarehouseNotFoundException.class
    })
    public ProblemDetail notFoundHandle(RuntimeException exception) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        p.setType(setTypeFromErrorCode(exception.getClass().getSimpleName()));
        return p;
    }

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
        String ERROR_URL = String.format("http://%s:8080/error-code/", domain);
        return new DefaultUriBuilderFactory()
                .uriString(ERROR_URL)
                .pathSegment(errorCode)
                .build();
    }
}
