package com.alotra.web.repository;

import com.alotra.web.entity.Topping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToppingRepository extends JpaRepository<Topping, Integer> {
    List<Topping> findByDeletedAtIsNull();
    List<Topping> findByDeletedAtIsNotNull();
    Topping findByNameIgnoreCaseAndDeletedAtIsNull(String name);
}