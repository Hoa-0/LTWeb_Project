package com.alotra.web.repository;

import com.alotra.web.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    
    /**
     * Tìm nhân viên theo username (không phân biệt hoa thường)
     */
    @Query("SELECT nv FROM NhanVien nv WHERE LOWER(nv.username) = LOWER(:username) AND nv.deletedAt IS NULL")
    Optional<NhanVien> findByUsernameIgnoreCase(@Param("username") String username);
    
    /**
     * Tìm nhân viên theo email (không phân biệt hoa thường)
     */
    @Query("SELECT nv FROM NhanVien nv WHERE LOWER(nv.email) = LOWER(:email) AND nv.deletedAt IS NULL")
    Optional<NhanVien> findByEmailIgnoreCase(@Param("email") String email);
    
    /**
     * Tìm nhân viên theo username hoặc email
     */
    @Query("SELECT nv FROM NhanVien nv WHERE (LOWER(nv.username) = LOWER(:usernameOrEmail) OR LOWER(nv.email) = LOWER(:usernameOrEmail))")
    Optional<NhanVien> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
    
    /**
     * Kiểm tra username đã tồn tại chưa
     */
    @Query("SELECT COUNT(nv) > 0 FROM NhanVien nv WHERE LOWER(nv.username) = LOWER(:username) AND nv.deletedAt IS NULL")
    boolean existsByUsernameIgnoreCase(@Param("username") String username);
    
    /**
     * Kiểm tra email đã tồn tại chưa  
     */
    @Query("SELECT COUNT(nv) > 0 FROM NhanVien nv WHERE LOWER(nv.email) = LOWER(:email) AND nv.deletedAt IS NULL")
    boolean existsByEmailIgnoreCase(@Param("email") String email);
    
    /**
     * Tìm nhân viên theo email và password hash
     */
    @Query("SELECT nv FROM NhanVien nv WHERE LOWER(nv.email) = LOWER(:email) AND nv.matKhauHash = :passwordHash AND nv.deletedAt IS NULL")
    Optional<NhanVien> findByEmailAndPasswordHash(@Param("email") String email, @Param("passwordHash") String passwordHash);
    
    /**
     * Tìm nhân viên theo username
     */
    @Query("SELECT nv FROM NhanVien nv WHERE LOWER(nv.username) = LOWER(:username) AND nv.deletedAt IS NULL")
    Optional<NhanVien> findByUsername(@Param("username") String username);
    
    /**
     * Tìm nhân viên theo email
     */
    @Query("SELECT nv FROM NhanVien nv WHERE LOWER(nv.email) = LOWER(:email) AND nv.deletedAt IS NULL")
    Optional<NhanVien> findByEmail(@Param("email") String email);
    
    /**
     * Tìm các nhân viên đã bị xóa (soft delete)
     */
    @Query("SELECT nv FROM NhanVien nv WHERE nv.deletedAt IS NOT NULL")
    List<NhanVien> findByDeletedAtIsNotNull();
    
    /**
     * Tìm nhân viên theo số điện thoại
     */
    @Query("SELECT nv FROM NhanVien nv WHERE nv.soDienThoai = :phone AND nv.deletedAt IS NULL")
    NhanVien findBySoDienThoai(@Param("phone") String phone);
    
    /**
     * Tìm kiếm nhân viên theo từ khóa, vai trò và trạng thái
     */
    @Query("SELECT nv FROM NhanVien nv WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           " LOWER(nv.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(nv.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(nv.tenNV) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:role IS NULL OR nv.vaiTro = :role) " +
           "AND (:status IS NULL OR nv.trangThai = :status) " +
           "AND nv.deletedAt IS NULL " +
           "ORDER BY nv.maNV DESC")
    List<NhanVien> searchEmployees(@Param("keyword") String keyword, 
                                   @Param("role") Integer role, 
                                   @Param("status") Integer status);
    
    /**
     * Tìm nhân viên active (chưa xóa)
     */
    @Query("SELECT nv FROM NhanVien nv WHERE nv.trangThai = :status AND nv.deletedAt IS NULL")
    List<NhanVien> findByTrangThaiAndDeletedAtIsNull(@Param("status") Byte status);
    
    /**
     * Debug: List all employees
     */
    @Query("SELECT nv FROM NhanVien nv")
    List<NhanVien> findAllEmployees();
}