package com.alotra.web.service;

import com.alotra.web.dto.dashboard.DashboardSummaryDTO;
import com.alotra.web.dto.dashboard.RecentOrderDTO;
import com.alotra.web.dto.dashboard.SeriesDTO;
import com.alotra.web.dto.dashboard.TopProductDTO;
import com.alotra.web.repository.CTDonHangRepository;
import com.alotra.web.repository.DonHangRepository;
import com.alotra.web.repository.SanPhamRepository;
import com.alotra.web.service.OrderStatusResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class DashboardService {

    private final SanPhamRepository sanPhamRepository;
    private final DonHangRepository donHangRepository;
    private final CTDonHangRepository ctDonHangRepository;
    private final OrderStatusResolver statusResolver;

    public DashboardSummaryDTO getSummary() {
        long totalProducts = sanPhamRepository.countAllActive();

        LocalDate today = LocalDate.now();
        LocalDateTime startDay = today.atStartOfDay();
        LocalDateTime endDay = today.atTime(LocalTime.MAX);
        long todayOrders = donHangRepository.countByNgayLapBetween(startDay, endDay);

        String delivered = statusResolver.resolveDeliveredStatus();
        String cancelled = statusResolver.resolveCancelledStatus();
        long pendingOrders = donHangRepository.countActiveNotDeliveredOrCancelled(delivered, cancelled);

        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDateTime startMonth = firstDayOfMonth.atStartOfDay();
        LocalDateTime endMonth = today.atTime(LocalTime.MAX);
        String paid = statusResolver.resolvePaymentStatus(true);
        BigDecimal monthlyRevenue = donHangRepository
                .sumTongThanhToanByNgayLapBetweenAndPaymentStatus(startMonth, endMonth, paid);
        if (monthlyRevenue == null) monthlyRevenue = BigDecimal.ZERO;

        return new DashboardSummaryDTO(totalProducts, todayOrders, pendingOrders, monthlyRevenue);
    }

    public List<RecentOrderDTO> getRecentOrders(int limit) {
        return donHangRepository
                .findAll(PageRequest.of(0, Math.max(1, Math.min(limit, 50)), Sort.by(Sort.Direction.DESC, "ngayLap")))
                .getContent()
                .stream()
                .map(d -> new RecentOrderDTO(
                        d.getMaDH(),
                        Optional.ofNullable(d.getTenNguoiNhan()).orElse(""),
                        d.getTongThanhToan(),
                        d.getTrangThaiDonHang(),
                        d.getNgayLap()
                ))
                .collect(Collectors.toList());
    }

    public SeriesDTO getRevenueLast7Days() {
        String paid = statusResolver.resolvePaymentStatus(true);
        LocalDate today = LocalDate.now();
        LocalDate sinceDate = today.minusDays(6); // include today -> 7 days window
        LocalDateTime since = sinceDate.atStartOfDay();

        // prepare map for 7 days with zero
        Map<LocalDate, BigDecimal> map = new LinkedHashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate d = sinceDate.plusDays(i);
            map.put(d, BigDecimal.ZERO);
        }

        for (Object[] row : donHangRepository.revenueByDaySince(since, paid)) {
            java.sql.Date day = (java.sql.Date) row[0];
            BigDecimal revenue = (row[1] == null) ? BigDecimal.ZERO : new BigDecimal(row[1].toString());
            LocalDate d = day.toLocalDate();
            if (map.containsKey(d)) {
                map.put(d, revenue);
            }
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM");
        List<String> labels = map.keySet().stream().map(d -> d.format(fmt)).collect(Collectors.toList());
        List<Number> data = map.values().stream().collect(Collectors.toList());
        return new SeriesDTO(labels, data);
    }

    public SeriesDTO getOrderStatusDistribution(int days) {
        LocalDateTime since = LocalDate.now().minusDays(Math.max(1, days)).atStartOfDay();
        List<Object[]> rows = donHangRepository.countByStatusSince(since);
        List<String> labels = new ArrayList<>();
        List<Number> data = new ArrayList<>();
        for (Object[] r : rows) {
            labels.add(Objects.toString(r[0], "N/A"));
            Number n;
            Object v = r[1];
            if (v instanceof Number) n = (Number) v; else n = Long.parseLong(v.toString());
            data.add(n);
        }
        return new SeriesDTO(labels, data);
    }

    public List<TopProductDTO> getTopProducts(int days, int limit) {
        LocalDateTime since = LocalDate.now().minusDays(Math.max(1, days)).atStartOfDay();
        List<Object[]> rows = ctDonHangRepository.topProductsSince(since);
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
        return all.stream().limit(Math.max(1, Math.min(limit, 20))).collect(Collectors.toList());
    }
}
