package com.alotra.web.repository;

import com.alotra.web.entity.Order;
import com.alotra.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderDetails od JOIN od.product p WHERE p.vendor.id = :vendorId")
    List<Order> findByVendorId(@Param("vendorId") Long vendorId);
    
    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderDetails od JOIN od.product p WHERE p.vendor.id = :vendorId ORDER BY o.id DESC")
    List<Order> findByVendorIdOrderByIdDesc(@Param("vendorId") Long vendorId);
    
    @Query("SELECT o FROM Order o ORDER BY o.id DESC")
    List<Order> findAllOrderByIdDesc();
    
    // Additional method for user orders
    @Query("SELECT o FROM Order o WHERE o.user = :user ORDER BY o.orderDate DESC")
    List<Order> findByUserOrderByOrderDateDesc(@Param("user") User user);
}