package com.alotra.web.controller;

import com.alotra.web.dto.CreateOrderRequest;
import com.alotra.web.dto.CreateOrderResponse;
import com.alotra.web.entity.DonHang;
import com.alotra.web.entity.NhanVien;
import com.alotra.web.service.PosOrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/NhanVien/donhang")
@RequiredArgsConstructor
@Slf4j
public class NhanVienDonHangApiController {

    private final PosOrderService posOrderService;

    @PostMapping("/tao")
    public ResponseEntity<?> taoDonHang(@RequestBody CreateOrderRequest request, HttpSession session) {
        try {
            Integer maNV = null;
            Object nv = session.getAttribute("loggedInNhanVien");
            if (nv instanceof NhanVien) {
                maNV = ((NhanVien) nv).getMaNV();
            }

            DonHang dh = posOrderService.createOrder(request, maNV);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CreateOrderResponse(dh.getMaDH(), dh.getTongHang(), dh.getTongThanhToan(), dh.getPaymentStatus()));
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid POS order payload: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            log.error("Error creating POS order", ex);
            String msg = ex.getMessage();
            if (msg == null || msg.isBlank()) msg = "Lỗi tạo đơn hàng";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }
}
