package com.alotra.web.repository;

import com.alotra.web.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Long> {

    List<ProductMedia> findByProductId(Integer productId);

    @Modifying
    @Query("UPDATE ProductMedia pm SET pm.isPrimary = false WHERE pm.productId = :productId")
    int clearPrimaryForProduct(@Param("productId") Integer productId);
}
