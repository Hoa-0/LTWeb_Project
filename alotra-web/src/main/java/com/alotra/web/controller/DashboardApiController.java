package com.alotra.web.controller;

import com.alotra.web.dto.dashboard.DashboardSummaryDTO;
import com.alotra.web.dto.dashboard.RecentOrderDTO;
import com.alotra.web.dto.dashboard.SeriesDTO;
import com.alotra.web.dto.dashboard.TopProductDTO;
import com.alotra.web.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/NhanVien/dashboard/api")
@RequiredArgsConstructor
public class DashboardApiController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> summary(HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/recent-orders")
    public ResponseEntity<List<RecentOrderDTO>> recentOrders(@RequestParam(defaultValue = "5") int limit,
                                                             HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(dashboardService.getRecentOrders(limit));
    }

    @GetMapping("/revenue-7-days")
    public ResponseEntity<SeriesDTO> revenue7Days(HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(dashboardService.getRevenueLast7Days());
    }

    @GetMapping("/order-statuses")
    public ResponseEntity<SeriesDTO> orderStatuses(@RequestParam(defaultValue = "7") int days,
                                                   HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(dashboardService.getOrderStatusDistribution(days));
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDTO>> topProducts(@RequestParam(defaultValue = "30") int days,
                                                           @RequestParam(defaultValue = "5") int limit,
                                                           HttpSession session) {
        if (session.getAttribute("loggedInNhanVien") == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(dashboardService.getTopProducts(days, limit));
    }
}
