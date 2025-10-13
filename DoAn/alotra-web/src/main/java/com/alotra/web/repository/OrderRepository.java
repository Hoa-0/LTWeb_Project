package com.alotra.web.repository;

import com.alotra.web.entity.Order;
import com.alotra.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Spring Data JPA sẽ tự động tạo query để tìm các đơn hàng của một user,
    // sắp xếp theo ngày đặt hàng mới nhất ở trên
    List<Order> findByUserOrderByOrderDateDesc(User user);
}