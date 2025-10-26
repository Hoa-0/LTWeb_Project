package com.alotra.web.repository;

import com.alotra.web.entity.CTDonHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CTDonHangRepository extends JpaRepository<CTDonHang, Integer> {
    // Count order lines that belong to variants of the specified product
    long countByVariant_Product_Id(Integer productId);
}