package com.alotra.web.repository;

import com.alotra.web.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.Tuple; // <-- Import Tuple

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.alotra.web.dto.admin.RevenueOverTimeDTO; // Import DTO mới

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

    // Đếm số đơn hàng trong một khoảng thời gian (ví dụ: hôm nay)
    long countByNgayLapBetween(LocalDateTime startTime, LocalDateTime endTime);

    // Tính tổng doanh thu trong một khoảng thời gian (chỉ tính đơn hàng đã giao thành công)
    @Query("SELECT COALESCE(SUM(dh.tongThanhToan), 0) FROM DonHang dh WHERE dh.trangThaiDonHang = :status AND dh.ngayLap BETWEEN :startTime AND :endTime")
    BigDecimal sumTongThanhToanByTrangThaiDonHangAndNgayLapBetween(
            @Param("status") String status,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    // Có thể thêm các phương thức khác nếu cần
    
    
    
    
 // === PHƯƠNG THỨC MỚI CHO BÁO CÁO ===
    
//    @Query("SELECT COALESCE(SUM(dh.tongThanhToan), 0) FROM DonHang dh " +
//            "WHERE dh.trangThaiDonHang = :status " +
//            "AND dh.paymentStatus = :paymentStatus " + // <-- SỬA LỖI: Thêm điều kiện lọc paymentStatus
//            "AND dh.ngayLap BETWEEN :startTime AND :endTime")
//     BigDecimal sumTongThanhToanByTrangThaiDonHangAndPaymentStatusAndNgayLapBetween( // <-- SỬA LỖI: Đổi tên phương thức cho rõ ràng
//             @Param("status") String status,
//             @Param("paymentStatus") String paymentStatus, // <-- SỬA LỖI: Thêm tham số paymentStatus
//             @Param("startTime") LocalDateTime startTime,
//             @Param("endTime") LocalDateTime endTime);

    /**
     * Lấy dữ liệu doanh thu theo từng ngày trong khoảng thời gian.
     * Chỉ tính đơn hàng 'DaGiao' và 'DaThanhToan'.
     * SỬA LỖI: Dùng CAST(... AS DATE) thay vì FUNCTION('DATE', ...) cho SQL Server.
     */
    @Query("SELECT " +
           "   CAST(dh.ngayLap AS DATE) AS reportDate, " + // <-- SỬA LỖI Ở ĐÂY
           "   SUM(dh.tongThanhToan) AS totalRevenue " +
           "FROM DonHang dh " +
           "WHERE dh.trangThaiDonHang = 'DaGiao' AND dh.paymentStatus = 'DaThanhToan' " +
           "AND dh.ngayLap BETWEEN :startTime AND :endTime " +
           "GROUP BY CAST(dh.ngayLap AS DATE) " + // <-- SỬA LỖI Ở ĐÂY
           "ORDER BY CAST(dh.ngayLap AS DATE) ASC") // <-- SỬA LỖI Ở ĐÂY
    List<Tuple> getRevenueOverTimeTuple(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    
    
 // === BỔ SUNG: Phương thức đếm đơn hàng hoàn thành ===
    /**
     * Đếm số đơn hàng đã hoàn thành (đã giao VÀ đã thanh toán) trong khoảng thời gian.
     */
//     @Query("SELECT COUNT(dh.maDH) FROM DonHang dh " +
//            "WHERE dh.trangThaiDonHang = :status " +
//            "AND dh.paymentStatus = :paymentStatus " +
//            "AND dh.ngayLap BETWEEN :startTime AND :endTime")
//    long countCompletedOrdersBetween(
//             @Param("status") String status,
//             @Param("paymentStatus") String paymentStatus,
//             @Param("startTime") LocalDateTime startTime,
//             @Param("endTime") LocalDateTime endTime);
//     
     
     
     @Query("SELECT COALESCE(SUM(dh.tongThanhToan), 0) FROM DonHang dh " +
             "WHERE dh.trangThaiDonHang = :status " +
             "AND dh.paymentStatus = :paymentStatus " +
             "AND dh.ngayLap BETWEEN :startTime AND :endTime")
      BigDecimal sumTongThanhToanByTrangThaiDonHangAndPaymentStatusAndNgayLapBetween(
              @Param("status") String status,
              @Param("paymentStatus") String paymentStatus,
              @Param("startTime") LocalDateTime startTime,
              @Param("endTime") LocalDateTime endTime);

       @Query("SELECT COUNT(dh.maDH) FROM DonHang dh " +
              "WHERE dh.trangThaiDonHang = :status " +
              "AND dh.paymentStatus = :paymentStatus " +
              "AND dh.ngayLap BETWEEN :startTime AND :endTime")
      long countCompletedOrdersBetween(
               @Param("status") String status,
               @Param("paymentStatus") String paymentStatus,
               @Param("startTime") LocalDateTime startTime,
               @Param("endTime") LocalDateTime endTime);
  
     
}