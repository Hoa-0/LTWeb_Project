package com.alotra.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String name;
    private double price;
    private int quantity;
    // Thêm trường imageUrl nếu bạn muốn hiển thị ảnh trong giỏ hàng
}