package com.alotra.web.repository;

import com.alotra.web.entity.GioHangCT;
import com.alotra.web.entity.GioHangCTTopping;
import com.alotra.web.entity.GioHangCTToppingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GioHangCTToppingRepository extends JpaRepository<GioHangCTTopping, GioHangCTToppingId> {
    List<GioHangCTTopping> findByCartItem(GioHangCT cartItem);
}