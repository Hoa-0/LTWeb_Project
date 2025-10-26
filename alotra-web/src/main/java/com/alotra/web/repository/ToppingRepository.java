package com.alotra.web.repository;

import com.alotra.web.entity.Topping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Integer> {

    @Query("SELECT t FROM Topping t WHERE t.deletedAt IS NULL AND t.trangThai = 1 ORDER BY t.tenTopping")
    List<Topping> findAllActive();

    List<Topping> findByDeletedAtIsNull();
    Topping findByTenToppingIgnoreCaseAndDeletedAtIsNull(String tenTopping);
    
    /**
     * Tìm các topping đã bị xóa (soft delete)
     */
    List<Topping> findByDeletedAtIsNotNull();
}
