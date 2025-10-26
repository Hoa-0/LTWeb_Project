package com.alotra.web.service;

import com.alotra.web.dto.CreateOrderRequest;
import com.alotra.web.entity.DonHang;

public interface PosOrderService {
    DonHang createOrder(CreateOrderRequest request, Integer maNV);
}
