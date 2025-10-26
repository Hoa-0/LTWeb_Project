package com.alotra.web.service;

import com.alotra.web.dto.dashboard.SeriesDTO;
import com.alotra.web.dto.dashboard.TopProductDTO;
import com.alotra.web.dto.revenue.RevenueSummaryDTO;
import com.alotra.web.repository.CTDonHangRepository;
import com.alotra.web.repository.DonHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final DonHangRepository donHangRepository;
    private final CTDonHangRepository ctDonHangRepository;
    private final OrderStatusResolver statusResolver;

    public RevenueSummaryDTO getSummary(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);

        long totalOrders = donHangRepository.countByNgayLapBetween(start, end);

        String paid = statusResolver.resolvePaymentStatus(true);
        String delivered = statusResolver.resolveDeliveredStatus();
        String cancelled = statusResolver.resolveCancelledStatus();

        long paidOrders = donHangRepository.countByPaymentStatusBetween(start, end, paid);
        long deliveredOrders = donHangRepository.countByStatusBetween(start, end, delivered);
        long cancelledOrders = donHangRepository.countByStatusBetween(start, end, cancelled);

        BigDecimal revenuePaid = donHangRepository
                .sumTongThanhToanByNgayLapBetweenAndPaymentStatus(start, end, paid);
        if (revenuePaid == null) revenuePaid = BigDecimal.ZERO;
        BigDecimal avgPaid = paidOrders > 0 ? revenuePaid.divide(BigDecimal.valueOf(paidOrders), java.math.RoundingMode.HALF_UP) : BigDecimal.ZERO;

        return new RevenueSummaryDTO(from, to, totalOrders, paidOrders, deliveredOrders, cancelledOrders, revenuePaid, avgPaid);
    }

    public SeriesDTO getRevenueByDay(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);
        String paid = statusResolver.resolvePaymentStatus(true);

        Map<LocalDate, BigDecimal> map = new LinkedHashMap<>();
        LocalDate cursor = from;
        while (!cursor.isAfter(to)) {
            map.put(cursor, BigDecimal.ZERO);
            cursor = cursor.plusDays(1);
        }

        for (Object[] row : donHangRepository.revenueByDayBetween(start, end, paid)) {
            java.sql.Date day = (java.sql.Date) row[0];
            BigDecimal revenue = (row[1] == null) ? BigDecimal.ZERO : new BigDecimal(row[1].toString());
            LocalDate d = day.toLocalDate();
            if (map.containsKey(d)) map.put(d, revenue);
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM");
        List<String> labels = map.keySet().stream().map(d -> d.format(fmt)).collect(Collectors.toList());
        List<Number> data = map.values().stream().collect(Collectors.toList());
        return new SeriesDTO(labels, data);
    }

    public List<TopProductDTO> getTopProducts(LocalDate from, LocalDate to, int limit) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);
        List<Object[]> rows = ctDonHangRepository.topProductsBetween(start, end);
        List<TopProductDTO> all = new ArrayList<>();
        for (Object[] r : rows) {
            String name = Objects.toString(r[0], "");
            long sales = 0;
            if (r[1] != null) {
                sales = (r[1] instanceof Number) ? ((Number) r[1]).longValue() : Long.parseLong(r[1].toString());
            }
            BigDecimal revenue = (r[2] == null) ? BigDecimal.ZERO : new BigDecimal(r[2].toString());
            all.add(new TopProductDTO(name, sales, revenue));
        }
        return all.stream().limit(Math.max(1, Math.min(limit, 50))).collect(Collectors.toList());
    }
}
