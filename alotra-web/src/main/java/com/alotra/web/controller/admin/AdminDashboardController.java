package com.alotra.web.controller.admin;

import com.alotra.web.dto.admin.DashboardStatsDTO;
import com.alotra.web.service.admin.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.NumberFormat;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest; // <-- Import
import org.springframework.ui.Model; // <-- Import Model

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping
    public String showDashboard(Model model,HttpServletRequest request) {
        DashboardStatsDTO stats = adminDashboardService.getDashboardStatistics();

        // Định dạng tiền tệ Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        model.addAttribute("donHangHomNay", stats.getDonHangHomNay());
        model.addAttribute("doanhThuHomNay", currencyFormatter.format(stats.getDoanhThuHomNay())); // Định dạng tiền tệ
        model.addAttribute("tongSanPham", stats.getTongSanPham());
        model.addAttribute("tongKhachHang", stats.getTongKhachHang());
        model.addAttribute("tongNhanVien", stats.getTongNhanVien()); // Thêm số nhân viên
     // === THÊM DÒNG NÀY ===
        model.addAttribute("requestURI", request.getRequestURI());

        // Lấy tên admin từ security context (tùy chọn)
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String currentUserName = authentication.getName();
        // model.addAttribute("adminName", currentUserName);

        return "admin/dashboard";
    }
}