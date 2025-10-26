package com.alotra.web.repository;

import com.alotra.web.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByDeletedAtIsNull();
    Category findByNameIgnoreCaseAndDeletedAtIsNull(String name);
    
    /**
     * Tìm các category đã bị xóa (soft delete)
     */
    List<Category> findByDeletedAtIsNotNull();
}