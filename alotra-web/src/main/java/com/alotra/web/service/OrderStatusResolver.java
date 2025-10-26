package com.alotra.web.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resolve DonHang status values that comply with SQL Server CHECK constraints.
 * If constraints are not present/detectable, falls back to reasonable defaults.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderStatusResolver {

    private final JdbcTemplate jdbcTemplate;

    private List<String> allowedPaymentStatus = new ArrayList<>();
    private List<String> allowedTrangThaiDonHang = new ArrayList<>();

    @Value("${app.order.status.delivered:DaGiao}")
    private String deliveredPreferred;

    @Value("${app.order.status.cancelled:DaHuy}")
    private String cancelledPreferred;

    @PostConstruct
    public void init() {
        try {
            String sql = "SELECT con.name AS constraint_name, con.definition " +
                    "FROM sys.check_constraints con " +
                    "JOIN sys.tables t ON con.parent_object_id = t.object_id " +
                    "WHERE t.name = 'DonHang'";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

            Map<String, List<String>> allowed = new HashMap<>();
            allowed.put("PaymentStatus", new ArrayList<>());
            allowed.put("TrangThaiDonHang", new ArrayList<>());

            Pattern pattern = Pattern.compile("\\((?:\\[)?(PaymentStatus|TrangThaiDonHang)(?:\\])?\\)\\s*IN\\s*\\((.*?)\\)", Pattern.CASE_INSENSITIVE);
            for (Map<String, Object> r : rows) {
                String def = Objects.toString(r.get("definition"), "");
                Matcher m = pattern.matcher(def);
                while (m.find()) {
                    String col = m.group(1);
                    String list = m.group(2);
                    String[] parts = list.split(",");
                    for (String p : parts) {
                        String v = p.trim();
                        if (v.startsWith("'")) v = v.substring(1);
                        if (v.endsWith("'")) v = v.substring(0, v.length() - 1);
                        if (!v.isEmpty() && !allowed.get(col).contains(v)) {
                            allowed.get(col).add(v);
                        }
                    }
                }
            }

            this.allowedPaymentStatus = allowed.get("PaymentStatus");
            this.allowedTrangThaiDonHang = allowed.get("TrangThaiDonHang");

            if (!allowedPaymentStatus.isEmpty() || !allowedTrangThaiDonHang.isEmpty()) {
                log.info("Detected DonHang constraints. PaymentStatus allowed: {} | TrangThaiDonHang allowed: {}",
                        allowedPaymentStatus, allowedTrangThaiDonHang);
            } else {
                log.info("No explicit DonHang CHECK constraints detected; using defaults");
            }
        } catch (Exception e) {
            log.warn("Could not inspect DonHang check constraints: {}", e.getMessage());
        }
    }

    public String resolveInitialOrderStatus() {
        ensureLoaded();
        // Preferred candidates for initial order state
        List<String> candidates = List.of(
                "ChoThanhToan", "CHO_THANH_TOAN", "ChoXuLy", "PENDING", "DangXuLy", "DANG_XU_LY"
        );
        return pickBest(allowedTrangThaiDonHang, candidates, "ChoThanhToan");
    }

    public String resolvePaymentStatus(boolean payNow) {
        ensureLoaded();
        if (payNow) {
            List<String> paidCands = List.of("DaThanhToan", "PAID", "Paid", "THANH_TOAN_XONG");
            return pickBest(allowedPaymentStatus, paidCands, "DaThanhToan");
        } else {
            List<String> unpaidCands = List.of("ChuaThanhToan", "UNPAID", "Unpaid", "PENDING");
            return pickBest(allowedPaymentStatus, unpaidCands, "ChuaThanhToan");
        }
    }

    public String resolveDeliveredStatus() {
        ensureLoaded();
        List<String> cands = new ArrayList<>();
        if (deliveredPreferred != null && !deliveredPreferred.isBlank()) cands.add(deliveredPreferred);
        cands.addAll(deliveredSynonyms());
        return pickBest(allowedTrangThaiDonHang, cands, deliveredPreferred != null && !deliveredPreferred.isBlank() ? deliveredPreferred : "DaGiao");
    }

    public String resolveCancelledStatus() {
        ensureLoaded();
        List<String> cands = new ArrayList<>();
        if (cancelledPreferred != null && !cancelledPreferred.isBlank()) cands.add(cancelledPreferred);
        cands.addAll(cancelledSynonyms());
        return pickBest(allowedTrangThaiDonHang, cands, cancelledPreferred != null && !cancelledPreferred.isBlank() ? cancelledPreferred : "DaHuy");
    }

    private static String pickBest(List<String> allowed, List<String> candidates, String fallback) {
        if (allowed != null && !allowed.isEmpty()) {
            // try exact first (case-sensitive), then case-insensitive, else first allowed
            for (String c : candidates) {
                if (allowed.contains(c)) return c;
            }
            for (String c : candidates) {
                for (String a : allowed) {
                    if (a.equalsIgnoreCase(c)) return a; // keep DB value's exact casing
                }
            }
            return allowed.get(0);
        }
        return fallback;
    }

    public List<String> getAllowedPaymentStatus() {
        ensureLoaded();
        return Collections.unmodifiableList(allowedPaymentStatus);
    }

    public List<String> getAllowedTrangThaiDonHang() {
        ensureLoaded();
        return Collections.unmodifiableList(allowedTrangThaiDonHang);
    }

    private void ensureLoaded() {
        if ((allowedPaymentStatus == null || allowedPaymentStatus.isEmpty()) &&
            (allowedTrangThaiDonHang == null || allowedTrangThaiDonHang.isEmpty())) {
            // Try reload once
            try { init(); } catch (Exception ignored) { }
        }
    }

    private static List<String> deliveredSynonyms() {
        return List.of(
                "DaGiao", "DA_GIAO", "DA GIAO", "Delivered", "DELIVERED",
                "HoanThanh", "HOAN_THANH", "Hoàn thành", "COMPLETED", "Completed",
                "GiaoThanhCong", "Giao hang thanh cong", "Giao hàng thành công", "GiaoHangThanhCong"
        );
    }

    private static List<String> cancelledSynonyms() {
        return List.of(
                "DaHuy", "DA_HUY", "DA HUY", "Huy", "HỦY", "Hủy",
                "Cancelled", "CANCELLED", "Canceled", "CANCELED"
        );
    }

    public boolean isDelivered(String value) {
        if (value == null) return false;
        String v = value.trim();
        for (String s : deliveredSynonyms()) {
            if (v.equals(s) || v.equalsIgnoreCase(s)) return true;
        }
        // also compare with resolved delivered
        String resolved = resolveDeliveredStatus();
        return v.equals(resolved) || v.equalsIgnoreCase(resolved);
    }

    public boolean isCancelled(String value) {
        if (value == null) return false;
        String v = value.trim();
        for (String s : cancelledSynonyms()) {
            if (v.equals(s) || v.equalsIgnoreCase(s)) return true;
        }
        String resolved = resolveCancelledStatus();
        return v.equals(resolved) || v.equalsIgnoreCase(resolved);
    }
}
