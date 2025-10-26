package com.alotra.web.repository;

import com.alotra.web.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    NhanVien findByUsername(String username);
    NhanVien findByEmail(String email);
    NhanVien findByPhone(String phone);

    @Query("SELECT n FROM NhanVien n WHERE n.deletedAt IS NULL AND " +
            "(:kw IS NULL OR LOWER(n.username) LIKE LOWER(CONCAT('%',:kw,'%')) OR LOWER(n.email) LIKE LOWER(CONCAT('%',:kw,'%')) OR LOWER(n.fullName) LIKE LOWER(CONCAT('%',:kw,'%')) OR n.phone LIKE CONCAT('%',:kw,'%')) AND " +
            "(:role IS NULL OR n.role = :role) AND " +
            "(:status IS NULL OR n.status = :status)")
    List<NhanVien> search(@Param("kw") String kw, @Param("role") Integer role, @Param("status") Integer status);

    List<NhanVien> findByDeletedAtIsNull();
    List<NhanVien> findByDeletedAtIsNotNull();
=======
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
>>>>>>> e66511f5f76c02a6c7a92993afb90a3d8655037c
}