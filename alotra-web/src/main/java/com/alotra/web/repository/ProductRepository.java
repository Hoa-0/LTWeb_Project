package com.alotra.web.repository;

import com.alotra.web.entity.Category;
import com.alotra.web.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query(value = "SELECT TOP 10 sp.* FROM SanPham sp " +
           "JOIN BienTheSanPham bt ON bt.MaSP = sp.MaSP " +
           "JOIN CTDonHang ct ON ct.MaBT = bt.MaBT " +
           "GROUP BY sp.MaSP, sp.TenSP, sp.MoTa, sp.TrangThai, sp.AnhSP " +
           "ORDER BY SUM(ct.SoLuong) DESC", nativeQuery = true)
    List<Product> findBestSellersNative();
    
    @Query(value = "SELECT sp.* FROM SanPham sp WHERE sp.MaDM = :categoryId AND sp.TrangThai = 1", nativeQuery = true)
    List<Product> findListByCategoryNative(@Param("categoryId") Integer categoryId);
    
    @Query(value = "SELECT sp.* FROM SanPham sp WHERE sp.TenSP LIKE %:keyword% AND sp.TrangThai = 1", nativeQuery = true)
    List<Product> searchByKeywordNative(@Param("keyword") String keyword);
    
    long countByCategoryAndDeletedAtIsNull(Category category);
    
    /**
     * Tìm sản phẩm theo tên (không phân biệt hoa thường) và chưa bị xóa
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) = LOWER(:name) AND p.deletedAt IS NULL")
    Optional<Product> findByNameIgnoreCaseAndDeletedAtIsNull(@Param("name") String name);
    
    /**
     * Tìm kiếm sản phẩm cho admin
     */
    @Query("SELECT p FROM Product p WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
           "AND (:status IS NULL OR p.status = :status) " +
           "ORDER BY p.id DESC")
    List<Product> adminSearch(@Param("keyword") String keyword, @Param("categoryId") Integer categoryId, @Param("status") Integer status);
    
    /**
     * Tìm các sản phẩm đã bị xóa (soft delete)
     */
    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NOT NULL")
    List<Product> findByDeletedAtIsNotNull();
}