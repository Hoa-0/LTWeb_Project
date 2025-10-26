package com.alotra.web.repository;

import com.alotra.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.role = :role")
    Iterable<User> findByRole(@Param("role") String role);
    
    @Query("SELECT u FROM User u WHERE u.role = 'ROLE_VENDOR' AND u.shopActive = true")
    Iterable<User> findActiveVendors();
    
    /**
     * Tìm user theo username (không phân biệt hoa thường)
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username) AND u.deletedAt IS NULL")
    Optional<User> findByUsernameIgnoreCase(@Param("username") String username);
    
    /**
     * Tìm user theo email (không phân biệt hoa thường)
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email) AND u.deletedAt IS NULL")
    Optional<User> findByEmailIgnoreCase(@Param("email") String email);
    
    /**
     * Tìm user theo username hoặc email
     */
    @Query("SELECT u FROM User u WHERE (LOWER(u.username) = LOWER(:usernameOrEmail) OR LOWER(u.email) = LOWER(:usernameOrEmail)) AND u.deletedAt IS NULL")
    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
    
    /**
     * Kiểm tra username đã tồn tại chưa
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE LOWER(u.username) = LOWER(:username) AND u.deletedAt IS NULL")
    boolean existsByUsernameIgnoreCase(@Param("username") String username);
    
    /**
     * Kiểm tra email đã tồn tại chưa
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE LOWER(u.email) = LOWER(:email) AND u.deletedAt IS NULL")
    boolean existsByEmailIgnoreCase(@Param("email") String email);
}