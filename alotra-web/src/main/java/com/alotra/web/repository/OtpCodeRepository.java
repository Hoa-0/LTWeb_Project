package com.alotra.web.repository;

import com.alotra.web.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> { // Changed to Long
    
    @Query("SELECT o FROM OtpCode o WHERE o.email = :email AND o.code = :code AND o.used = false AND o.expiresAt > :now")
    Optional<OtpCode> findValidOtp(@Param("email") String email, @Param("code") String code, @Param("now") LocalDateTime now);
    
    @Query("SELECT o FROM OtpCode o WHERE o.email = :email AND o.type = :type AND o.used = false AND o.expiresAt > :now ORDER BY o.createdAt DESC")
    Optional<OtpCode> findLatestValidOtp(@Param("email") String email, @Param("type") String type, @Param("now") LocalDateTime now);
    
    void deleteByEmailAndType(String email, String type);

    // Additional method for compatibility  
    @Query("SELECT o FROM OtpCode o WHERE o.email = :email AND o.type = :type AND o.code = :code ORDER BY o.id DESC")
    Optional<OtpCode> findTopByCustomerAndTypeAndCodeOrderByIdDesc(@Param("email") String email, @Param("type") String type, @Param("code") String code);
    
    // Method for OtpService compatibility
    void deleteByEmailAndTypeAndExpiresAtBefore(String email, String type, LocalDateTime expiredTime);
}