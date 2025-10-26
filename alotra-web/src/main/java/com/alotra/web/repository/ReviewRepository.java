package com.alotra.web.repository;

import com.alotra.web.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
    /**
     * Tìm reviews theo sản phẩm
     */
    @Query("SELECT r FROM Review r WHERE r.maSP = :productId AND r.deletedAt IS NULL ORDER BY r.createdAt DESC")
    List<Review> findByProductId(@Param("productId") Integer productId);
    
    /**
     * Tìm reviews theo khách hàng
     */
    @Query("SELECT r FROM Review r WHERE r.maKH = :customerId AND r.deletedAt IS NULL ORDER BY r.createdAt DESC")
    List<Review> findByCustomerId(@Param("customerId") Integer customerId);
    
    /**
     * Tính rating trung bình của sản phẩm
     */
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.maSP = :productId AND r.deletedAt IS NULL")
    Double findAverageRatingByProductId(@Param("productId") Integer productId);
    
    /**
     * Đếm số reviews của sản phẩm
     */
    @Query("SELECT COUNT(r) FROM Review r WHERE r.maSP = :productId AND r.deletedAt IS NULL")
    Long countByProductId(@Param("productId") Integer productId);
    
    /**
     * Kiểm tra khách hàng đã review sản phẩm chưa
     */
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.maKH = :customerId AND r.maSP = :productId AND r.deletedAt IS NULL")
    boolean existsByCustomerIdAndProductId(@Param("customerId") Integer customerId, @Param("productId") Integer productId);
}