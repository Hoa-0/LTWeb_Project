package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_details")
@Getter
@Setter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private double price; // Lưu lại giá tại thời điểm mua

    // Nhiều chi tiết đơn hàng thuộc về một đơn hàng
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Nhiều chi tiết đơn hàng tham chiếu đến một sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}