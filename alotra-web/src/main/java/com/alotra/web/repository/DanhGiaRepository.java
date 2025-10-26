package com.alotra.web.repository;

import com.alotra.web.entity.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
    
    List<DanhGia> findByMaKH(Integer maKH);

    // Find reviews for a product by joining through order line -> variant -> product
    @Query("SELECT dg FROM DanhGia dg JOIN dg.ctDonHang ct JOIN ct.bienTheSanPham bt " +
        "WHERE bt.maSP = :maSP")
    List<DanhGia> findByMaSP(@Param("maSP") Integer maSP);

    @Query("SELECT dg FROM DanhGia dg JOIN dg.ctDonHang ct JOIN ct.bienTheSanPham bt " +
        "WHERE dg.maKH = :maKH AND bt.maSP = :maSP")
    List<DanhGia> findByMaKHAndMaSP(@Param("maKH") Integer maKH, @Param("maSP") Integer maSP);

    @Query("SELECT dg FROM DanhGia dg JOIN dg.ctDonHang ct JOIN ct.bienTheSanPham bt " +
        "WHERE bt.maSP = :maSP AND dg.trangThai = 1 ORDER BY dg.ngayDanhGia DESC")
    List<DanhGia> findActiveBySanPham(@Param("maSP") Integer maSP);

    @Query("SELECT AVG(dg.soSao) FROM DanhGia dg JOIN dg.ctDonHang ct JOIN ct.bienTheSanPham bt " +
        "WHERE bt.maSP = :maSP AND dg.trangThai = 1")
    Double getAverageRatingBySanPham(@Param("maSP") Integer maSP);

    @Query("SELECT new com.alotra.web.repository.DanhGiaRepository$ProductRatingStats(" +
        "AVG(dg.soSao), COUNT(dg)) FROM DanhGia dg JOIN dg.ctDonHang ct JOIN ct.bienTheSanPham bt " +
        "WHERE bt.maSP = :productId AND dg.trangThai = 1")
    ProductRatingStats findStatsByProductId(@Param("productId") Integer productId);

    // Additional methods for compatibility
    @Query("SELECT dg FROM DanhGia dg WHERE dg.maKH = :customerId AND dg.maCT IN :lineIds")
    List<DanhGia> findByCustomer_IdAndOrderLine_IdIn(@Param("customerId") Integer customerId, 
                                                     @Param("lineIds") List<Integer> lineIds);
    
    @Query("SELECT dg FROM DanhGia dg WHERE dg.maKH = :customerId AND dg.maCT = :lineId")
    Optional<DanhGia> findByCustomer_IdAndOrderLine_Id(@Param("customerId") Integer customerId, 
                                                       @Param("lineId") Integer lineId);
    
    @Query("SELECT dg FROM DanhGia dg JOIN dg.ctDonHang ct JOIN ct.bienTheSanPham bt " +
        "WHERE bt.maSP = :productId ORDER BY dg.ngayDanhGia DESC")
    List<DanhGia> findByProductIdOrderByCreatedAtDesc(@Param("productId") Integer productId);

    // Admin review management methods
    @Query("SELECT dg FROM DanhGia dg ORDER BY dg.ngayDanhGia DESC")
    List<DanhGia> findAllOrderByCreatedAtDesc();

    class ProductRatingStats {
        private final Double averageRating;
        private final Long totalReviews;
        
        public ProductRatingStats(Double averageRating, Long totalReviews) {
            this.averageRating = averageRating;
            this.totalReviews = totalReviews;
        }
        
        public Double getAverageRating() { return averageRating; }
        public Long getTotalReviews() { return totalReviews; }
        
        // Compatibility methods
        public Double getAvg() { return averageRating; }
        public Long getCnt() { return totalReviews; }
    }
}