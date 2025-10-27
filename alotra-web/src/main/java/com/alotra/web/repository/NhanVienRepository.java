package com.alotra.web.repository;

import com.alotra.web.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    @Query("SELECT nv FROM NhanVien nv WHERE (LOWER(nv.username) = LOWER(:usernameOrEmail) OR LOWER(nv.email) = LOWER(:usernameOrEmail)) AND nv.deletedAt IS NULL")
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
    
    /*Dùng cho CustomerEmployeeDetailsService.java*/
    Optional<NhanVien> findByUsernameOrEmail(String username, String email);
    
    //*Checkpoint3
    // Đếm tổng số nhân viên đang active và chưa bị xóa mềm
    long countByTrangThaiAndDeletedAtIsNull(Byte trangThai);
    
    
    
}