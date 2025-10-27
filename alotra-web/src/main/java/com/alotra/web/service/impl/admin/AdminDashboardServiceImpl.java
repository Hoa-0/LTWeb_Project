package com.alotra.web.service.impl.admin;

import com.alotra.web.dto.admin.DashboardStatsDTO; // Giả định bạn đã có DTO này
import com.alotra.web.repository.DonHangRepository;
import com.alotra.web.repository.KhachHangRepository; // Giả định có method countByTrangThai
import com.alotra.web.repository.NhanVienRepository; // Giả định có method countByTrangThaiAndDeletedAtIsNull
import com.alotra.web.repository.SanPhamRepository; // Giả định có method countByTrangThaiAndDeletedAtIsNull
import com.alotra.web.service.admin.AdminDashboardService; // Giả định bạn đã có Interface này
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    // Thêm hằng số để code dễ đọc hơn
    private static final String STATUS_DELIVERED = "DaGiao";
    private static final String STATUS_PAID = "DaThanhToan";
    private static final byte ACTIVE_STATUS = (byte) 1;


    @Override
    public DashboardStatsDTO getDashboardStatistics() {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        // Lấy số liệu
        // Đếm TẤT CẢ đơn hàng được tạo hôm nay
        long donHangHomNay = donHangRepository.countByNgayLapBetween(startOfDay, endOfDay);

        // === SỬA LỖI TÍNH DOANH THU ===
        // Tính doanh thu chỉ từ các đơn đã giao VÀ đã thanh toán trong ngày
        BigDecimal doanhThuHomNay = donHangRepository.sumTongThanhToanByTrangThaiDonHangAndPaymentStatusAndNgayLapBetween(
                STATUS_DELIVERED, // Trạng thái đơn hàng
                STATUS_PAID,      // Trạng thái thanh toán
                startOfDay,
                endOfDay);

        // Giả định các repository có các phương thức count tương ứng
        long tongKhachHang = khachHangRepository.countByTrangThai(ACTIVE_STATUS); // Đếm khách hàng active
        long tongNhanVien = nhanVienRepository.countByTrangThaiAndDeletedAtIsNull(ACTIVE_STATUS); // Đếm nhân viên active & chưa xóa
        long tongSanPham = sanPhamRepository.countByTrangThaiAndDeletedAtIsNull(ACTIVE_STATUS); // Đếm sản phẩm active & chưa xóa

        // Đảm bảo doanhThuHomNay không null nếu không có đơn nào
        if (doanhThuHomNay == null) {
            doanhThuHomNay = BigDecimal.ZERO;
        }


        // Giả định DashboardStatsDTO nhận các tham số theo thứ tự này
        return new DashboardStatsDTO(
                donHangHomNay,
                doanhThuHomNay,
                tongSanPham,
                tongKhachHang,
                tongNhanVien
        );
    }
}