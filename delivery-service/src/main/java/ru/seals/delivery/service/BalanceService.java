package ru.seals.delivery.service;

import ru.seals.delivery.dto.UpdateBalanceDTO;
import ru.seals.delivery.model.BalanceHistory;

public interface BalanceService {
    BalanceHistory withdraw(String kcId, UpdateBalanceDTO dto);
    BalanceHistory deposit(String kcId, UpdateBalanceDTO dto);
}
