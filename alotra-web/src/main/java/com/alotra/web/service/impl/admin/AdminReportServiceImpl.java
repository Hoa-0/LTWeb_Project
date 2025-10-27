package com.alotra.web.service.impl.admin;

import com.alotra.web.dto.admin.CategoryRevenueDTO;
import com.alotra.web.dto.admin.RevenueOverTimeDTO;
import com.alotra.web.dto.admin.TopProductDTO;
import com.alotra.web.entity.DanhMucSanPham;
import com.alotra.web.repository.CTDonHangRepository;
import com.alotra.web.repository.DanhMucSanPhamRepository;
import com.alotra.web.repository.DonHangRepository;
import com.alotra.web.service.admin.AdminReportService;
import jakarta.persistence.Tuple; // <-- Import Tuple
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date; // <-- Import java.sql.Date
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList; // <-- Import ArrayList
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminReportServiceImpl implements AdminReportService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private CTDonHangRepository ctDonHangRepository;

    @Autowired
    private DanhMucSanPhamRepository danhMucSanPhamRepository;

    private static final String STATUS_DELIVERED = "DaGiao";
    private static final String STATUS_PAID = "DaThanhToan";
    private static final byte ACTIVE_STATUS = (byte) 1;


    @Override
    @Transactional(readOnly = true)
    public List<DanhMucSanPham> getActiveCategories() {
        return danhMucSanPhamRepository.findByDeletedAtIsNullOrderByTenDMAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevenueOverTimeDTO> getRevenueOverTimeData(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        // Gọi phương thức Repository trả về Tuple
        List<Tuple> results = donHangRepository.getRevenueOverTimeTuple(startDateTime, endDateTime);

        // --- SỬA LỖI: Map từ Tuple sang DTO ---
        List<RevenueOverTimeDTO> dtoList = new ArrayList<>();
        for (Tuple row : results) {
            // Lấy dữ liệu từ Tuple bằng alias đã đặt trong query
            // Lưu ý: Kiểu trả về của FUNCTION('DATE',...) có thể là java.sql.Date
            Object dateObject = row.get("reportDate");
            BigDecimal totalRevenue = row.get("totalRevenue", BigDecimal.class); // Chỉ định kiểu

            LocalDate reportDate = null;
            if (dateObject instanceof java.sql.Date sqlDate) {
                 reportDate = sqlDate.toLocalDate();
            } else if (dateObject instanceof LocalDate localDate) {
                 reportDate = localDate;
            }
            // Thêm các kiểu khác nếu cần (ví dụ java.util.Date)

            if (reportDate != null && totalRevenue != null) {
                dtoList.add(new RevenueOverTimeDTO(reportDate, totalRevenue));
            }
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopProductDTO> getTopSellingProductsData(LocalDate startDate, LocalDate endDate, Integer categoryId, int limit) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        Pageable topLimit = PageRequest.of(0, limit);
        // Giả sử phương thức này trong CTDonHangRepository dùng SELECT NEW và hoạt động tốt
        return ctDonHangRepository.findTopSellingProducts(startDateTime, endDateTime, categoryId, topLimit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryRevenueDTO> getRevenueByCategoryData(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
         // Giả sử phương thức này trong CTDonHangRepository dùng SELECT NEW và hoạt động tốt
        return ctDonHangRepository.findRevenueByCategory(startDateTime, endDateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getKpiData(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        BigDecimal totalRevenue = donHangRepository.sumTongThanhToanByTrangThaiDonHangAndPaymentStatusAndNgayLapBetween(
                STATUS_DELIVERED, STATUS_PAID, startDateTime, endDateTime);

        long totalOrders = donHangRepository.countCompletedOrdersBetween(
               STATUS_DELIVERED, STATUS_PAID, startDateTime, endDateTime);

        BigDecimal averageOrderValue = BigDecimal.ZERO;
        if (totalOrders > 0) {
            averageOrderValue = totalRevenue.divide(BigDecimal.valueOf(totalOrders), 0, RoundingMode.HALF_UP);
        }

         // Đảm bảo không null
         if (totalRevenue == null) { totalRevenue = BigDecimal.ZERO; }

        Map<String, Object> kpiData = new HashMap<>();
        kpiData.put("totalRevenue", totalRevenue);
        kpiData.put("totalOrders", totalOrders);
        kpiData.put("averageOrderValue", averageOrderValue);

        return kpiData;
    }
}