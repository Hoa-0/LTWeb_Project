package com.alotra.web.dto.admin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private long donHangHomNay;
    private BigDecimal doanhThuHomNay;
    private long tongSanPham; // Sẽ cần SanPhamRepository sau
    private long tongKhachHang;
    private long tongNhanVien; // Thêm số lượng nhân viên
}