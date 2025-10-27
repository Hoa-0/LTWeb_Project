package com.alotra.web.service.admin;

import com.alotra.web.dto.admin.CategoryRevenueDTO;
import com.alotra.web.dto.admin.RevenueOverTimeDTO;
import com.alotra.web.dto.admin.TopProductDTO;
import com.alotra.web.entity.DanhMucSanPham; // Import DanhMucSanPham

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map; // Import Map

public interface AdminReportService {

    /** Lấy danh sách tất cả danh mục đang hoạt động */
    List<DanhMucSanPham> getActiveCategories();

    /** Lấy dữ liệu doanh thu theo ngày */
    List<RevenueOverTimeDTO> getRevenueOverTimeData(LocalDate startDate, LocalDate endDate);

    /** Lấy danh sách top sản phẩm bán chạy (ví dụ top 10) */
    List<TopProductDTO> getTopSellingProductsData(LocalDate startDate, LocalDate endDate, Integer categoryId, int limit);

    /** Lấy dữ liệu doanh thu theo danh mục */
    List<CategoryRevenueDTO> getRevenueByCategoryData(LocalDate startDate, LocalDate endDate);

    /** Lấy các thẻ KPI tổng hợp */
    Map<String, Object> getKpiData(LocalDate startDate, LocalDate endDate);
}