package com.alotra.web.repository;

import com.alotra.web.entity.Product;
import com.alotra.web.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    
    List<ProductVariant> findByProductId(Long productId);
    
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.product.id = :productId AND pv.isActive = true")
    List<ProductVariant> findActiveByProductId(@Param("productId") Long productId);
    
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.isActive = true")
    List<ProductVariant> findAllActive();
    
    // Additional compatibility methods
    List<ProductVariant> findByProduct(Product product);
    
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.product = :product ORDER BY pv.size")
    List<ProductVariant> findByProductFetchingSize(@Param("product") Product product);
}