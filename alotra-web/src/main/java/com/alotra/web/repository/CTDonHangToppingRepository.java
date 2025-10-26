package com.alotra.web.repository;

import com.alotra.web.entity.CTDonHangTopping;
import com.alotra.web.entity.CTDonHangToppingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTDonHangToppingRepository extends JpaRepository<CTDonHangTopping, CTDonHangToppingId> {
	List<CTDonHangTopping> findByIdMaCTOrderByIdMaToppingAsc(Integer maCT);
}
