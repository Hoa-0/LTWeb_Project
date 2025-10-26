package com.alotra.web.repository;

import com.alotra.web.entity.GioHang;
import com.alotra.web.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    Optional<GioHang> findFirstByCustomerAndStatus(KhachHang customer, String status);
}
