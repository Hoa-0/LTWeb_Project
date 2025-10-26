package com.alotra.web.repository;

import com.alotra.web.entity.BienTheSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BienTheSanPhamRepository extends JpaRepository<BienTheSanPham, Integer> {
    
    // Tìm biến thể theo sản phẩm
    @Query("SELECT bt FROM BienTheSanPham bt WHERE bt.maSP = :maSP")
    List<BienTheSanPham> findByMaSP(@Param("maSP") Integer maSP);
    
    // Tìm biến thể active theo sản phẩm
    @Query("SELECT bt FROM BienTheSanPham bt WHERE bt.maSP = :maSP AND bt.trangThai = 1")
    List<BienTheSanPham> findByMaSPAndActive(@Param("maSP") Integer maSP);
    
    // Xóa tất cả biến thể của sản phẩm (explicit JPQL to ensure execution order)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM BienTheSanPham bt WHERE bt.maSP = :maSP")
    void deleteByMaSP(@Param("maSP") Integer maSP);
}