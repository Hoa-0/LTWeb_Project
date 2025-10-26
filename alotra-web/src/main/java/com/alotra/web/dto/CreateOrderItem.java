package com.alotra.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItem {
    private Integer maBT; // BienTheSanPham id
    private Integer soLuong;
    private BigDecimal giamGiaDong; // default 0
    private String ghiChu;
    private List<CreateOrderItemTopping> toppings = new ArrayList<>();
}
