package ru.seals.dripid.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.seals.dripid.exception.DefaultMessageNotFoundException;
import ru.seals.dripid.exception.MessageTypeNotFoundException;
import ru.seals.dripid.exception.WarehouseNotFoundException;

import java.net.URI;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
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

    private URI setTypeFromErrorCode(String errorCode) {
        String ERROR_URL = "http://localhost:8080/error-code/";
        return new DefaultUriBuilderFactory()
                .uriString(ERROR_URL)
                .pathSegment(errorCode)
                .build();
    }
}
