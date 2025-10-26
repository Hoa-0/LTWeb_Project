package com.alotra.web.repository;

import com.alotra.web.entity.SuKienKhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SuKienKhuyenMaiRepository extends JpaRepository<SuKienKhuyenMai, Integer> {

    @Query("SELECT km FROM SuKienKhuyenMai km WHERE km.deletedAt IS NULL ORDER BY km.maKM DESC")
    Page<SuKienKhuyenMai> findAllNotDeleted(Pageable pageable);

    @Query("SELECT COUNT(km) FROM SuKienKhuyenMai km WHERE km.deletedAt IS NULL")
    long countAllNotDeleted();

    @Query("SELECT COUNT(km) FROM SuKienKhuyenMai km WHERE km.deletedAt IS NULL AND km.trangThai = 1 AND :today BETWEEN km.ngayBD AND km.ngayKT")
    long countActive(@Param("today") LocalDate today);

    @Query("SELECT COUNT(km) FROM SuKienKhuyenMai km WHERE km.deletedAt IS NULL AND km.ngayKT < :today")
    long countExpired(@Param("today") LocalDate today);

    @Query("SELECT COUNT(km) FROM SuKienKhuyenMai km WHERE km.deletedAt IS NULL AND km.trangThai = 1 AND km.ngayKT BETWEEN :today AND :soon")
    long countExpiringSoon(@Param("today") LocalDate today, @Param("soon") LocalDate soon);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE SuKienKhuyenMai km SET km.trangThai = 0 WHERE km.deletedAt IS NULL AND km.trangThai = 1 AND km.ngayKT < :today")
    int deactivateExpired(@Param("today") LocalDate today);
}
