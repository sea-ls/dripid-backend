package ru.seals.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.seals.delivery.dto.BalanceHistoryDTO;
import ru.seals.delivery.dto.UpdateBalanceDTO;
import ru.seals.delivery.model.BalanceHistory;
import ru.seals.delivery.service.BalanceService;
import ru.seals.delivery.service.KeycloakService;
import ru.seals.delivery.util.Converter;

@RestController
@RequestMapping(value = "/api/delivery-service/person/balance")
@RequiredArgsConstructor
@CrossOrigin
public class PersonBalanceController {
    private final BalanceService balanceService;
    private final Converter converter;

    private final KeycloakService keycloakService;
    @PostMapping("deposit/authenticated")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Пользователь сам пополняет баланс")
    public BalanceHistoryDTO depositAuthenticated(@RequestBody UpdateBalanceDTO dto) {
        String kcIdAuth = keycloakService.getKeycloakUserId();
        BalanceHistory bh = balanceService.updateUserBalance(kcIdAuth, dto);
        return converter.mapBHIntoDTO(bh);
    }
    @PostMapping("deposit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Пополнение баланса пользователя")
    public BalanceHistoryDTO deposit(@PathVariable String id,
                                     @RequestBody UpdateBalanceDTO dto) {
        BalanceHistory bh = balanceService.updateUserBalance(id, dto);
        return converter.mapBHIntoDTO(bh);
    }
    @PostMapping("withdraw/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Опустошение баланса пользователя")
    public BalanceHistoryDTO withdraw(@PathVariable String id,
                                      @RequestBody UpdateBalanceDTO dto) {
        BalanceHistory bh = balanceService.updateUserBalance(id, dto);
        return converter.mapBHIntoDTO(bh);
    }
}
