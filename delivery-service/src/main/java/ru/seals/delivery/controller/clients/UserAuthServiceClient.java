package ru.seals.delivery.controller.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.seals.delivery.dto.UserDTO;

@FeignClient(name = "auth-service", url = "http://auth-service:8080/api/auth-service/user")
public interface UserAuthServiceClient {
    @GetMapping("{id}")
    UserDTO getById(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken,
                    @PathVariable String id);
    @GetMapping("authenticated")
    UserDTO getAuthenticatedUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken);
}
