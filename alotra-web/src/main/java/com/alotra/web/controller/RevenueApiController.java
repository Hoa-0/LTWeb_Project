package com.alotra.web.controller;

import com.alotra.web.dto.dashboard.SeriesDTO;
import com.alotra.web.dto.dashboard.TopProductDTO;
import com.alotra.web.dto.revenue.RevenueSummaryDTO;
import com.alotra.web.service.RevenueService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/NhanVien/doanhthu/api")
@RequiredArgsConstructor
public class RevenueApiController {

    private final RevenueService revenueService;

    @GetMapping("/summary")
    public ResponseEntity<RevenueSummaryDTO> summary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                     HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(revenueService.getSummary(from, to));
    }

    @GetMapping("/revenue-by-day")
    public ResponseEntity<SeriesDTO> revenueByDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                  HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(revenueService.getRevenueByDay(from, to));
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDTO>> topProducts(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                           @RequestParam(defaultValue = "5") int limit,
                                                           HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(revenueService.getTopProducts(from, to, limit));
    }
}
