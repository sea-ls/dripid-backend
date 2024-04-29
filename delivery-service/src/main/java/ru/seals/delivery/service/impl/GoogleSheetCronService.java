package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.seals.delivery.service.GoogleSheetService;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleSheetCronService {
    // TODO: 28.04.2024 Значение временное
    private static final String UPDATE_AT_4AM = "0 0 4 * * *";
    private final GoogleSheetService googleSheetService;

    /**
     * Обновление курса валют каждый день в 4 утра.
     */
    @Scheduled(cron = UPDATE_AT_4AM)
    public void updateCurrencyValues() {
        googleSheetService.updateCurrencyValues();
    }

}
