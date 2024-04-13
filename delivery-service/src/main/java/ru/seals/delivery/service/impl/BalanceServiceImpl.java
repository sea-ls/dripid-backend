package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.seals.delivery.dto.UpdateBalanceDTO;
import ru.seals.delivery.exception.NotEnoughMoneyException;
import ru.seals.delivery.model.BalanceHistory;
import ru.seals.delivery.model.Person;
import ru.seals.delivery.model.enums.TransactionType;
import ru.seals.delivery.repository.BalanceHistoryRepository;
import ru.seals.delivery.service.BalanceService;
import ru.seals.delivery.service.PersonService;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    private final PersonService ps;
    private final BalanceHistoryRepository bhRepo;
    private Money getMoneyRub(BigDecimal amount) {
        return Money.of(amount, "RUB");
    }
    private String getKeycloakUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    private Money subtract(Money bal, BigDecimal amount) {
        Money newBalance = bal.subtract(getMoneyRub(amount));
        if (newBalance.isNegative()) {
            throw new NotEnoughMoneyException("Not enough money");
        }
        return newBalance;
    }
    private Money add(Money bal, BigDecimal amount) {
        return bal.add(getMoneyRub(amount));
    }
    private BalanceHistory updateUserBalance(String kcId, UpdateBalanceDTO dto) {
        Person person = ps.getByKeycloakId(kcId);
        Money oldBalance = person.getBalance();
        Money newBalance = null;

        if (dto.getType().equals(TransactionType.WITHDRAW))
            newBalance = subtract(oldBalance, dto.getAmount());
        if (dto.getType().equals(TransactionType.DEPOSIT))
            newBalance = add(oldBalance, dto.getAmount());

        person.setBalance(newBalance);
        ps.save(person);

        return bhRepo.saveAndFlush(new BalanceHistory(dto.getType(),
                person,
                kcId.equals(getKeycloakUserId()) ? null : ps.getAuthenticated(),
                oldBalance,
                newBalance,
                dto.getCheque()));
    }

    @Override
    public BalanceHistory withdraw(String kcId, UpdateBalanceDTO dto) {
        return updateUserBalance(kcId, dto);
    }

    @Override
    public BalanceHistory deposit(String kcId, UpdateBalanceDTO dto) {
        return updateUserBalance(kcId, dto);
    }
}
