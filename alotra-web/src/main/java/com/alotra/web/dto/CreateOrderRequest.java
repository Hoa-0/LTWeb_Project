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
public class CreateOrderRequest {
    private Integer maKH; // optional; fallback to guest
    private String paymentMethod; // e.g., CASH, CARD, MOMO
    private Boolean payNow; // if true -> PAID + PaidAt now

    private BigDecimal giamGiaDon; // default 0
    private BigDecimal phiVanChuyen; // default 0

    private String ghiChu;
    private String phuongThucNhanHang;
    private String diaChiNhanHang;
    private String tenNguoiNhan;
    private String sdtNguoiNhan;

    private List<CreateOrderItem> items = new ArrayList<>();
}
