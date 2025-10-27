package com.alotra.web.controller.admin;

import com.alotra.web.dto.admin.CategoryRevenueDTO;
import com.alotra.web.dto.admin.RevenueOverTimeDTO;
import com.alotra.web.dto.admin.TopProductDTO;
import com.alotra.web.entity.DanhMucSanPham;
import com.alotra.web.service.admin.AdminReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/reports")
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
public class AdminReportController {

    @Autowired
    private AdminReportService reportService;

    @GetMapping("/revenue")
    public String showRevenueReport(
            // Tham số lọc, có giá trị mặc định
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "month") String range, // Thêm range cho bộ lọc nhanh
            Model model) {

        // --- Xử lý Date Range ---
        LocalDate today = LocalDate.now();
        if (startDate == null || endDate == null) {
            switch (range.toLowerCase()) {
                case "today":
                    startDate = today;
                    endDate = today;
                    break;
                case "week":
                    startDate = today.minusDays(6); // 7 ngày tính cả hôm nay
                    endDate = today;
                    break;
                case "year":
                    startDate = today.with(TemporalAdjusters.firstDayOfYear());
                    endDate = today.with(TemporalAdjusters.lastDayOfYear());
                    break;
                case "month": // Mặc định là tháng này
                default:
                    startDate = today.with(TemporalAdjusters.firstDayOfMonth());
                    endDate = today.with(TemporalAdjusters.lastDayOfMonth());
                    break;
            }
        }
        // Đảm bảo endDate không trước startDate
         if (endDate.isBefore(startDate)) {
             endDate = startDate;
         }


        // --- Lấy dữ liệu từ Service ---
        List<DanhMucSanPham> categories = reportService.getActiveCategories();
        Map<String, Object> kpiData = reportService.getKpiData(startDate, endDate);
        List<RevenueOverTimeDTO> revenueOverTime = reportService.getRevenueOverTimeData(startDate, endDate);
        List<TopProductDTO> topProducts = reportService.getTopSellingProductsData(startDate, endDate, categoryId, 10); // Lấy top 10
        List<CategoryRevenueDTO> revenueByCategory = reportService.getRevenueByCategoryData(startDate, endDate);


        // --- Đưa dữ liệu vào Model ---
        model.addAttribute("categories", categories);
        model.addAttribute("kpiData", kpiData);
        model.addAttribute("revenueOverTime", revenueOverTime);
        model.addAttribute("topProducts", topProducts);
        model.addAttribute("revenueByCategory", revenueByCategory);

        // Đưa các giá trị lọc hiện tại vào model để hiển thị trên form
        model.addAttribute("selectedStartDate", startDate);
        model.addAttribute("selectedEndDate", endDate);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedRange", range); // Để active nút lọc nhanh

        return "admin/report-revenue"; // Trả về view HTML
    }
}