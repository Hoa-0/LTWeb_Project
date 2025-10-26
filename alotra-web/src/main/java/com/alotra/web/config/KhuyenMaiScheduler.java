package com.alotra.web.config;

import com.alotra.web.repository.SuKienKhuyenMaiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class KhuyenMaiScheduler {

    private final SuKienKhuyenMaiRepository suKienRepo;

    // Chạy hàng ngày lúc 02:00
    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void deactivateExpiredPromotions() {
        int updated = 0;
        try {
            updated = suKienRepo.deactivateExpired(LocalDate.now());
        } catch (Exception ex) {
            log.warn("Error deactivating expired promotions: {}", ex.getMessage());
        }
        if (updated > 0) {
            log.info("Auto-deactivated {} expired promotions", updated);
        }
    }
}
