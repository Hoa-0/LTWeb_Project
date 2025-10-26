package com.alotra.web.repository;

import com.alotra.web.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Integer>, JpaSpecificationExecutor<DonHang> {

    long countByNgayLapBetween(LocalDateTime start, LocalDateTime end);

    @Query("select coalesce(sum(d.tongThanhToan), 0) from DonHang d where d.ngayLap between :start and :end and d.paymentStatus = :paidStatus")
    BigDecimal sumTongThanhToanByNgayLapBetweenAndPaymentStatus(@Param("start") LocalDateTime start,
								@Param("end") LocalDateTime end,
								@Param("paidStatus") String paidStatus);

    @Query(value = "SELECT CONVERT(date, NgayLap) as day, SUM(TongThanhToan) as revenue " +
	    "FROM DonHang WHERE NgayLap >= :since AND PaymentStatus = :paidStatus " +
	    "GROUP BY CONVERT(date, NgayLap) ORDER BY day", nativeQuery = true)
    List<Object[]> revenueByDaySince(@Param("since") LocalDateTime since, @Param("paidStatus") String paidStatus);

    @Query(value = "SELECT CONVERT(date, NgayLap) as day, SUM(TongThanhToan) as revenue " +
        "FROM DonHang WHERE NgayLap BETWEEN :start AND :end AND PaymentStatus = :paidStatus " +
        "GROUP BY CONVERT(date, NgayLap) ORDER BY day", nativeQuery = true)
    List<Object[]> revenueByDayBetween(@Param("start") LocalDateTime start,
                       @Param("end") LocalDateTime end,
                       @Param("paidStatus") String paidStatus);

    @Query(value = "SELECT TrangThaiDonHang as status, COUNT(*) as cnt " +
	    "FROM DonHang WHERE NgayLap >= :since GROUP BY TrangThaiDonHang", nativeQuery = true)
    List<Object[]> countByStatusSince(@Param("since") LocalDateTime since);

    @Query("select count(d) from DonHang d where d.trangThaiDonHang <> :delivered and d.trangThaiDonHang <> :cancelled")
    long countActiveNotDeliveredOrCancelled(@Param("delivered") String delivered,
					    @Param("cancelled") String cancelled);

    @Query("select count(d) from DonHang d where d.ngayLap between :start and :end and d.trangThaiDonHang = :status")
    long countByStatusBetween(@Param("start") LocalDateTime start,
                  @Param("end") LocalDateTime end,
                  @Param("status") String status);

    @Query("select count(d) from DonHang d where d.ngayLap between :start and :end and d.paymentStatus = :paymentStatus")
    long countByPaymentStatusBetween(@Param("start") LocalDateTime start,
                     @Param("end") LocalDateTime end,
                     @Param("paymentStatus") String paymentStatus);
}
