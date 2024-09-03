package ru.seals.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.seals.delivery.model.delivery.enums.OrderStatus;
import ru.seals.delivery.service.AdminService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminCronService {
    private static final String EVERY_12HRS = "0 0 */12 * * *";
    /**
     * contains old status and new status + upd time
     * in min, can be in days (prod)
     */
    private static final Map<OrderStatus, Object[]> UPD_STATUSES_MAP = Map.of(
            OrderStatus.WAREHOUSE_USA, new Object[]{OrderStatus.SHIPPED_RUSSIA, 2}
    );
    private final AdminService adminService;
    @Scheduled(cron = EVERY_12HRS)
    public void updOrdersStatus() {
        adminService.updOrderStatusesByUpdMap(UPD_STATUSES_MAP);
    }
}
