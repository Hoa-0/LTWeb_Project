package com.alotra.web.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DonHangConstraintLogger implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
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

            log.info("DonHang constraints -> PaymentStatus allowed: {}", allowed.get("PaymentStatus"));
            log.info("DonHang constraints -> TrangThaiDonHang allowed: {}", allowed.get("TrangThaiDonHang"));
        } catch (Exception e) {
            log.warn("Could not inspect DonHang check constraints: {}", e.getMessage());
        }
    }
}
