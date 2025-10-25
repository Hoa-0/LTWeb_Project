package com.alotra.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
@Slf4j
public class DebugDbController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/donhang-constraints")
    public Map<String, Object> inspectDonHangConstraints() {
        String sql = "SELECT con.name AS constraint_name, con.definition " +
                "FROM sys.check_constraints con " +
                "JOIN sys.tables t ON con.parent_object_id = t.object_id " +
                "WHERE t.name = 'DonHang'";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        Map<String, List<String>> allowed = new HashMap<>();
        allowed.put("PaymentStatus", new ArrayList<>());
        allowed.put("TrangThaiDonHang", new ArrayList<>());

        // regex to capture IN ('A','B',...) groups possibly around a specific column
        Pattern pattern = Pattern.compile("\\((?:\\[)?(PaymentStatus|TrangThaiDonHang)(?:\\])?\\)\\s*IN\\s*\\((.*?)\\)", Pattern.CASE_INSENSITIVE);

        for (Map<String, Object> r : rows) {
            String def = Objects.toString(r.get("definition"), "");
            Matcher m = pattern.matcher(def);
            while (m.find()) {
                String col = m.group(1);
                String list = m.group(2);
                // split by comma and strip quotes/spaces
                String[] parts = list.split(",");
                for (String p : parts) {
                    String v = p.trim();
                    if (v.startsWith("'")) v = v.substring(1);
                    if (v.endsWith("'")) v = v.substring(0, v.length()-1);
                    if (!v.isEmpty() && !allowed.get(col).contains(v)) {
                        allowed.get(col).add(v);
                    }
                }
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("allowedPaymentStatus", allowed.get("PaymentStatus"));
        result.put("allowedTrangThaiDonHang", allowed.get("TrangThaiDonHang"));
        result.put("raw", rows);
        return result;
    }
}
