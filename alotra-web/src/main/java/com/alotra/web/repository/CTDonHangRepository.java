package com.alotra.web.repository;

import com.alotra.web.entity.CTDonHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTDonHangRepository extends JpaRepository<CTDonHang, Integer> {
	List<CTDonHang> findByDonHang_MaDHOrderByMaCTAsc(Integer maDH);
}
