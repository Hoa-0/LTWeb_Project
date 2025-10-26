package com.alotra.web.repository;

import com.alotra.web.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    
    KhachHang findByEmail(String email);
    
    // Use entity attribute names (fields in KhachHang): tenDangNhap, soDienThoai
    KhachHang findByTenDangNhap(String tenDangNhap);

    KhachHang findBySoDienThoai(String soDienThoai);
    
    @Query("SELECT kh FROM KhachHang kh WHERE " +
           "(:kw IS NULL OR LOWER(kh.tenKH) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "LOWER(kh.email) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "LOWER(kh.tenDangNhap) LIKE LOWER(CONCAT('%', :kw, '%'))) AND " +
           "(:status IS NULL OR kh.trangThai = :status)")
    List<KhachHang> search(@Param("kw") String kw, @Param("status") Integer status);
}