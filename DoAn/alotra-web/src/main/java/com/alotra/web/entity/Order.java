package com.alotra.web.entity;

import com.alotra.web.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    private String shippingAddress;

    private double totalAmount;

    @Enumerated(EnumType.STRING) // Lưu trạng thái dưới dạng chuỗi (PENDING, CONFIRMED...)
    private OrderStatus status;

    // Một đơn hàng thuộc về một User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Một đơn hàng có nhiều chi tiết đơn hàng
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;
}