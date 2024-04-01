package ru.seals.delivery.security;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.security.access.AccessDeniedException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 403:
                return new AccessDeniedException("Access denied");
            default:
                return new Exception("Exception while getting product details");
        }
    }
}
