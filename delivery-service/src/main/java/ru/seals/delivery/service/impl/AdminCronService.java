package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.seals.delivery.service.AdminService;

@Service
@RequiredArgsConstructor
public class AdminCronService {
    private static final String EVERY_12HRS = "0 0 */12 * * *";
    private final AdminService adminService;
    @Scheduled(cron = EVERY_12HRS)
    public void updOrdersStatus() {
        adminService.updOrdersStatus();
    }
}
