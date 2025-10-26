package com.alotra.web.service;

import com.alotra.web.dto.CartItem;
import com.alotra.web.entity.Order;
import com.alotra.web.entity.OrderDetail;
import com.alotra.web.entity.Product;
import com.alotra.web.entity.User;
import com.alotra.web.enums.OrderStatus;
import com.alotra.web.repository.OrderRepository;
import com.alotra.web.repository.ProductRepository;
import com.alotra.web.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Order> findOrdersByUserEmail(String email) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        return orderRepository.findByUserOrderByOrderDateDesc(currentUser);
    }

    @Transactional
    public void createOrder(Map<Long, CartItem> cart, String userEmail, String shippingAddress) {
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Order order = new Order();
        order.setUser(currentUser);
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        Set<OrderDetail> orderDetails = new HashSet<>();
        for (CartItem item : cart.values()) {
            OrderDetail detail = new OrderDetail();
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

            detail.setProduct(product);
            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQuantity());
            detail.setOrder(order);

            orderDetails.add(detail);
        }
        order.setOrderDetails(orderDetails);

        double totalAmount = orderDetails.stream()
                .mapToDouble(d -> d.getPrice() * d.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);
    }
}