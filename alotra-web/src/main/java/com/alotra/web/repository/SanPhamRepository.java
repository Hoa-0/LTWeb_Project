package com.alotra.web.repository;

import com.alotra.web.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
	// THÊM DÒNG NÀY VÀO checkpoint3:
	long countByTrangThaiAndDeletedAtIsNull(Byte trangThai);

	// Tìm tất cả sản phẩm chưa bị xóa
	@Query("SELECT sp FROM SanPham sp WHERE sp.deletedAt IS NULL")
	List<SanPham> findAllActive();

	@Query("SELECT sp FROM SanPham sp WHERE sp.deletedAt IS NULL")
	Page<SanPham> findAllActive(Pageable pageable);

	// Tìm sản phẩm theo ID và chưa bị xóa
	@Query("SELECT sp FROM SanPham sp WHERE sp.maSP = :id AND sp.deletedAt IS NULL")
	Optional<SanPham> findByIdAndNotDeleted(@Param("id") Integer id);

	// Tìm sản phẩm theo danh mục
	@Query("SELECT sp FROM SanPham sp WHERE sp.maDM = :maDM AND sp.deletedAt IS NULL")
	List<SanPham> findByMaDMAndNotDeleted(@Param("maDM") Integer maDM);

	@Query("SELECT sp FROM SanPham sp WHERE sp.maDM = :maDM AND sp.deletedAt IS NULL")
	Page<SanPham> findByMaDMAndNotDeleted(@Param("maDM") Integer maDM, Pageable pageable);

	// Tìm sản phẩm theo trạng thái
	@Query("SELECT sp FROM SanPham sp WHERE sp.trangThai = :trangThai AND sp.deletedAt IS NULL")
	Page<SanPham> findByTrangThaiAndNotDeleted(@Param("trangThai") Byte trangThai, Pageable pageable);

	// Tìm kiếm sản phẩm theo tên
	@Query("SELECT sp FROM SanPham sp WHERE LOWER(sp.tenSP) LIKE LOWER(CONCAT('%', :tenSP, '%')) AND sp.deletedAt IS NULL")
	Page<SanPham> findByTenSPContainingIgnoreCaseAndNotDeleted(@Param("tenSP") String tenSP, Pageable pageable);

	// Tìm kiếm sản phẩm theo tên và danh mục
	@Query("SELECT sp FROM SanPham sp WHERE LOWER(sp.tenSP) LIKE LOWER(CONCAT('%', :tenSP, '%')) AND sp.maDM = :maDM AND sp.deletedAt IS NULL")
	Page<SanPham> findByTenSPContainingIgnoreCaseAndMaDMAndNotDeleted(@Param("tenSP") String tenSP,
			@Param("maDM") Integer maDM, Pageable pageable);

	// Đếm số sản phẩm theo danh mục
	@Query("SELECT COUNT(sp) FROM SanPham sp WHERE sp.maDM = :maDM AND sp.deletedAt IS NULL")
	long countByMaDMAndNotDeleted(@Param("maDM") Integer maDM);

	// Đếm số sản phẩm theo trạng thái
	@Query("SELECT COUNT(sp) FROM SanPham sp WHERE sp.trangThai = :trangThai AND sp.deletedAt IS NULL")
	long countByTrangThaiAndNotDeleted(@Param("trangThai") Byte trangThai);

	// Huy
	// Tổng số sản phẩm active chekcpoint3
	@Query("SELECT COUNT(sp) FROM SanPham sp WHERE sp.deletedAt IS NULL")
	long countAllActive();

	// Thêm query để lấy sản phẩm cùng danh mục và sắp xếp chekcpoint7
	// THAY THẾ findAllWithCategoryOrderByMaSPDesc bằng phương thức có phân trang
    // Spring Data JPA sẽ tự thêm ORDER BY DESC nếu không có OrderBy trong query
    @Query(value = "SELECT sp FROM SanPham sp JOIN FETCH sp.danhMuc",
           countQuery = "SELECT COUNT(sp) FROM SanPham sp") // Cần countQuery cho phân trang với JOIN FETCH
    Page<SanPham> findAllWithCategory(Pageable pageable);


 // THÊM PHƯƠNG THỨC SEARCH CÓ PHÂN TRANG
    @Query(value = "SELECT sp FROM SanPham sp JOIN FETCH sp.danhMuc " +
                   "WHERE LOWER(sp.tenSP) LIKE LOWER(CONCAT('%', :keyword, '%'))",
           countQuery = "SELECT COUNT(sp) FROM SanPham sp " + 
                        "WHERE LOWER(sp.tenSP) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<SanPham> searchByNameWithCategory(@Param("keyword") String keyword, Pageable pageable);
//Huy
	// (Thêm các phương thức khác nếu cần sau này)
	// Ví dụ: List<SanPham> findByNhanVienMaNV(Integer maNV); // Sẽ cần cho
	// Checkpoint 7
}