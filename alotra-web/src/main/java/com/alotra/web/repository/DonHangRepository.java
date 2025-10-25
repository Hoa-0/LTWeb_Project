package com.alotra.web.repository;

import com.alotra.web.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DonHangRepository extends JpaRepository<DonHang, Integer>, JpaSpecificationExecutor<DonHang> {
}
