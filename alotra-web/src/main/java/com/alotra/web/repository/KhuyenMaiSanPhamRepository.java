package com.alotra.web.repository;

import com.alotra.web.entity.KhuyenMaiSanPham;
import com.alotra.web.entity.KhuyenMaiSanPhamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhuyenMaiSanPhamRepository extends JpaRepository<KhuyenMaiSanPham, KhuyenMaiSanPhamId> {

    @Query("SELECT k FROM KhuyenMaiSanPham k WHERE k.maKM = :maKM")
    List<KhuyenMaiSanPham> findByMaKM(@Param("maKM") Integer maKM);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM KhuyenMaiSanPham k WHERE k.maSP = :maSP")
    void deleteByMaSP(@Param("maSP") Integer maSP);

    // Lấy phần trăm giảm cao nhất đang hiệu lực cho một sản phẩm
    // Điều kiện hiệu lực: sự kiện chưa xóa, trạng thái = 1 và ngày hiện tại nằm trong [NgayBD, NgayKT]
    @Query("SELECT COALESCE(MAX(k.phanTramGiam), 0) FROM KhuyenMaiSanPham k JOIN k.suKien s " +
            "WHERE k.maSP = :maSP AND s.deletedAt IS NULL AND s.trangThai = 1 AND :today BETWEEN s.ngayBD AND s.ngayKT")
    Integer findActiveDiscountPercentForProduct(@Param("maSP") Integer maSP, @Param("today") java.time.LocalDate today);

    // Xóa toàn bộ cấu hình sản phẩm của một sự kiện
    @Modifying
    @Query("DELETE FROM KhuyenMaiSanPham k WHERE k.maKM = :maKM")
    void deleteByMaKM(@Param("maKM") Integer maKM);
}
