package com.alotra.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateOrderResponse {
    private Integer maDH;
    private BigDecimal tongHang;
    private BigDecimal tongThanhToan;
    private String paymentStatus;
}
