package com.alotra.web.repository;

import com.alotra.web.entity.SizeSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeSanPhamRepository extends JpaRepository<SizeSanPham, Integer> {
    
    // Tìm tất cả size đang hoạt động
    @Query("SELECT sz FROM SizeSanPham sz WHERE sz.trangThai = 1 ORDER BY sz.tenSize")
    List<SizeSanPham> findAllActive();
    
    // Tìm size theo tên
    List<SizeSanPham> findByTenSizeIgnoreCase(String tenSize);
}