package com.alotra.web.repository;

import com.alotra.web.dto.admin.CategoryRevenueDTO; // Import DTO mới
import com.alotra.web.dto.admin.TopProductDTO;     // Import DTO mới
import com.alotra.web.entity.CTDonHang;
import org.springframework.data.domain.Pageable; // Import Pageable
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CTDonHangRepository extends JpaRepository<CTDonHang, Integer> {

    /**
     * Lấy danh sách sản phẩm bán chạy nhất trong khoảng thời gian.
     * Chỉ tính đơn hàng 'DaGiao' và 'DaThanhToan'.
     * Có thể lọc theo Mã Danh mục (categoryId).
     */
    @Query("SELECT new com.alotra.web.dto.admin.TopProductDTO(sp.tenSP, SUM(ct.soLuong), SUM(ct.thanhTien)) " +
           "FROM CTDonHang ct " +
           "JOIN ct.donHang dh " +
           "JOIN ct.bienTheSanPham bt " +
           "JOIN bt.sanPham sp " +
           "WHERE dh.trangThaiDonHang = 'DaGiao' AND dh.paymentStatus = 'DaThanhToan' " +
           "AND dh.ngayLap BETWEEN :startTime AND :endTime " +
           // Điều kiện lọc categoryId (nếu có)
           "AND (:categoryId IS NULL OR sp.maDM = :categoryId) " +
           "GROUP BY sp.tenSP " +
           "ORDER BY SUM(ct.thanhTien) DESC")
    List<TopProductDTO> findTopSellingProducts(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("categoryId") Integer categoryId, // Có thể là null
            Pageable pageable); // Dùng Pageable để giới hạn top (ví dụ: top 10)

    /**
     * Lấy tổng doanh thu theo từng danh mục trong khoảng thời gian.
     * Chỉ tính đơn hàng 'DaGiao' và 'DaThanhToan'.
     */
    @Query("SELECT new com.alotra.web.dto.admin.CategoryRevenueDTO(dm.tenDM, SUM(ct.thanhTien)) " +
           "FROM CTDonHang ct " +
           "JOIN ct.donHang dh " +
           "JOIN ct.bienTheSanPham bt " +
           "JOIN bt.sanPham sp " +
           "JOIN sp.danhMuc dm " + // JOIN đến DanhMucSanPham
           "WHERE dh.trangThaiDonHang = 'DaGiao' AND dh.paymentStatus = 'DaThanhToan' " +
           "AND dh.ngayLap BETWEEN :startTime AND :endTime " +
           "GROUP BY dm.tenDM " +
           "ORDER BY SUM(ct.thanhTien) DESC")
    List<CategoryRevenueDTO> findRevenueByCategory(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}