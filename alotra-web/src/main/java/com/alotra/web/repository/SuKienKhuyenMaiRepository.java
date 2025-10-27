package com.alotra.web.repository;

import com.alotra.web.entity.SuKienKhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying; // Thêm Modifying
import org.springframework.data.repository.query.Param; // Thêm Param
import java.time.LocalDateTime; // Bổ sung import này

import java.util.List;

@Repository
public interface SuKienKhuyenMaiRepository extends JpaRepository<SuKienKhuyenMai, Integer> {

    // Lấy danh sách khuyến mãi còn hoạt động và chưa bị xóa mềm
    List<SuKienKhuyenMai> findByTrangThaiAndDeletedAtIsNull(Byte trangThai);

    // Tìm kiếm theo tên (ví dụ)
    List<SuKienKhuyenMai> findByTenSuKienContainingIgnoreCaseAndDeletedAtIsNull(String tenSuKien);

    // Có thể thêm các query phức tạp hơn nếu cần
    // Ví dụ: Lấy các khuyến mãi đang diễn ra
    @Query("SELECT sk FROM SuKienKhuyenMai sk WHERE sk.trangThai = 1 AND sk.deletedAt IS NULL AND CURRENT_DATE BETWEEN sk.ngayBD AND sk.ngayKT")
    List<SuKienKhuyenMai> findActiveAndOngoingPromotions();

 // JpaRepository đã có sẵn Page<SuKienKhuyenMai> findAll(Pageable pageable)

    // === Cập nhật trạng thái trực tiếp bằng JPQL ===
    @Modifying
    @Query("UPDATE SuKienKhuyenMai sk SET sk.trangThai = :newStatus WHERE sk.maKM = :maKM AND sk.deletedAt IS NULL")
    int updateStatus(@Param("maKM") Integer maKM, @Param("newStatus") Byte newStatus);

    // === Xóa mềm trực tiếp bằng JPQL ===
    @Modifying
    @Query("UPDATE SuKienKhuyenMai sk SET sk.deletedAt = :deletedTime, sk.trangThai = 0 WHERE sk.maKM = :maKM")
    int softDelete(@Param("maKM") Integer maKM, @Param("deletedTime") LocalDateTime deletedTime);

    // === Khôi phục trực tiếp bằng JPQL ===
    @Modifying
    @Query("UPDATE SuKienKhuyenMai sk SET sk.deletedAt = NULL, sk.trangThai = 1 WHERE sk.maKM = :maKM")
    int restore(@Param("maKM") Integer maKM);

    // Query kiểm tra ràng buộc trước khi xóa vĩnh viễn (ví dụ)
    // @Query("SELECT CASE WHEN COUNT(kmsp) > 0 THEN TRUE ELSE FALSE END FROM KhuyenMaiSanPham kmsp WHERE kmsp.suKienKhuyenMai.maKM = :maKM")
    // boolean isPromotionLinkedToProducts(@Param("maKM") Integer maKM);
    
    
}