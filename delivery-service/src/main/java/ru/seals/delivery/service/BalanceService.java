package ru.seals.delivery.service;

import ru.seals.delivery.dto.UpdateBalanceDTO;
import ru.seals.delivery.model.BalanceHistory;

public interface BalanceService {
    BalanceHistory updateUserBalance(String kcId, UpdateBalanceDTO dto);
}
