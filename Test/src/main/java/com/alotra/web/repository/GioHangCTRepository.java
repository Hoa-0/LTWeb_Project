package com.alotra.web.repository;

import com.alotra.web.entity.GioHang;
import com.alotra.web.entity.GioHangCT;
import com.alotra.web.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GioHangCTRepository extends JpaRepository<GioHangCT, Integer> {
    List<GioHangCT> findByCart(GioHang cart);
    Optional<GioHangCT> findByCartAndVariant(GioHang cart, ProductVariant variant);
}
