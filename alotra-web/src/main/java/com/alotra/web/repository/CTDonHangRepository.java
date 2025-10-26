package com.alotra.web.repository;

import com.alotra.web.entity.CTDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CTDonHangRepository extends JpaRepository<CTDonHang, Integer> {
	List<CTDonHang> findByDonHang_MaDHOrderByMaCTAsc(Integer maDH);

	@Query(value = "SELECT sp.TenSP as name, SUM(ct.SoLuong) as sales, SUM(ct.ThanhTien) as revenue\n" +
			"FROM CTDonHang ct\n" +
			"JOIN DonHang dh ON dh.MaDH = ct.MaDH\n" +
			"JOIN BienTheSanPham bt ON bt.MaBT = ct.MaBT\n" +
			"JOIN SanPham sp ON sp.MaSP = bt.MaSP\n" +
			"WHERE dh.NgayLap >= :since\n" +
			"GROUP BY sp.TenSP\n" +
			"ORDER BY sales DESC", nativeQuery = true)
	List<Object[]> topProductsSince(@Param("since") java.time.LocalDateTime since);

    @Query(value = "SELECT sp.TenSP as name, SUM(ct.SoLuong) as sales, SUM(ct.ThanhTien) as revenue\n" +
	    "FROM CTDonHang ct\n" +
	    "JOIN DonHang dh ON dh.MaDH = ct.MaDH\n" +
	    "JOIN BienTheSanPham bt ON bt.MaBT = ct.MaBT\n" +
	    "JOIN SanPham sp ON sp.MaSP = bt.MaSP\n" +
	    "WHERE dh.NgayLap BETWEEN :start AND :end\n" +
	    "GROUP BY sp.TenSP\n" +
	    "ORDER BY sales DESC", nativeQuery = true)
    List<Object[]> topProductsBetween(@Param("start") java.time.LocalDateTime start,
				      @Param("end") java.time.LocalDateTime end);
}
