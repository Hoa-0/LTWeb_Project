package com.alotra.web.service.impl;

import com.alotra.web.dto.CreateOrderItem;
import com.alotra.web.dto.CreateOrderItemTopping;
import com.alotra.web.dto.CreateOrderRequest;
import com.alotra.web.entity.*;
import com.alotra.web.repository.*;
import com.alotra.web.service.PosOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PosOrderServiceImpl implements PosOrderService {

    private final DonHangRepository donHangRepository;
    private final CTDonHangRepository ctDonHangRepository;
    private final CTDonHangToppingRepository ctDonHangToppingRepository;
    private final BienTheSanPhamRepository bienTheSanPhamRepository;
    private final ToppingRepository toppingRepository;
    private final com.alotra.web.service.OrderStatusResolver statusResolver;
    private final KhuyenMaiSanPhamRepository khuyenMaiSanPhamRepository;

    @Value("${app.pos.guest-customer-id:1}")
    private Integer guestCustomerId;

    private static BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    @Override
    @Transactional
    public DonHang createOrder(CreateOrderRequest request, Integer maNV) {
        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Đơn hàng trống hoặc không hợp lệ");
        }

        BigDecimal giamGiaDon = nz(request.getGiamGiaDon());
        BigDecimal phiVanChuyen = nz(request.getPhiVanChuyen());

    // Map payment status to DB-allowed values (per CHECK constraint if present)
    String paymentStatus = statusResolver.resolvePaymentStatus(Boolean.TRUE.equals(request.getPayNow()));

    DonHang dh = DonHang.builder()
                .maKH(request.getMaKH() != null ? request.getMaKH() : guestCustomerId)
                .maNV(maNV)
                .ngayLap(LocalDateTime.now())
                // User request: always start with status 'DaGiao'
                .trangThaiDonHang("DaGiao")
        .paymentStatus(paymentStatus)
                .paymentMethod(request.getPaymentMethod())
                .paidAt(Boolean.TRUE.equals(request.getPayNow()) ? LocalDateTime.now() : null)
                .giamGiaDon(giamGiaDon.setScale(2, RoundingMode.HALF_UP))
                .phiVanChuyen(phiVanChuyen.setScale(2, RoundingMode.HALF_UP))
                .tongHang(BigDecimal.ZERO)
                .tongThanhToan(BigDecimal.ZERO)
                .ghiChu(request.getGhiChu())
                .phuongThucNhanHang(request.getPhuongThucNhanHang())
                .diaChiNhanHang(request.getDiaChiNhanHang())
                .tenNguoiNhan(request.getTenNguoiNhan())
                .sdtNguoiNhan(request.getSdtNguoiNhan())
                .build();

        // Persist header immediately to validate CHECK constraint and ensure MaDH is generated
        try {
            dh = donHangRepository.saveAndFlush(dh);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            String m = String.valueOf(e.getMessage());
            if (m.contains("CHECK constraint") && m.contains("TrangThaiDonHang")) {
                throw new IllegalStateException(
                        "TrangThaiDonHang không hợp lệ với DB. Giá trị được phép: " + statusResolver.getAllowedTrangThaiDonHang()
                );
            }
            throw e;
        }

        BigDecimal tongHang = BigDecimal.ZERO;

        for (CreateOrderItem item : request.getItems()) {
            if (item.getMaBT() == null || item.getSoLuong() == null || item.getSoLuong() <= 0) {
                throw new IllegalArgumentException("Dòng đơn hàng không hợp lệ");
            }

            var btOpt = bienTheSanPhamRepository.findById(item.getMaBT());
            if (btOpt.isEmpty()) {
                throw new IllegalArgumentException("Biến thể sản phẩm không tồn tại: MaBT=" + item.getMaBT());
            }

            BienTheSanPham bt = btOpt.get();
            if (bt.getTrangThai() == null || bt.getTrangThai() != 1) {
                throw new IllegalStateException("Biến thể không hoạt động: MaBT=" + item.getMaBT());
            }

            BigDecimal donGia = bt.getGiaBan().setScale(2, RoundingMode.HALF_UP);

            // Toppings per unit
            List<CreateOrderItemTopping> toppings = item.getToppings();
            BigDecimal toppingPerUnit = BigDecimal.ZERO;
            if (toppings != null) {
                for (CreateOrderItemTopping t : toppings) {
                    if (t.getMaTopping() == null || t.getSoLuong() == null || t.getSoLuong() < 0) {
                        throw new IllegalArgumentException("Topping không hợp lệ trong dòng đơn hàng");
                    }
                    var top = toppingRepository.findById(t.getMaTopping())
                            .orElseThrow(() -> new IllegalArgumentException("Topping không tồn tại: " + t.getMaTopping()));
                    if (top.getTrangThai() == null || top.getTrangThai() != 1) {
                        throw new IllegalStateException("Topping không hoạt động: " + t.getMaTopping());
                    }
                    BigDecimal giaThem = top.getGiaThem().setScale(2, RoundingMode.HALF_UP);
                    toppingPerUnit = toppingPerUnit.add(giaThem.multiply(BigDecimal.valueOf(t.getSoLuong())));
                }
            }

            // Tính giảm giá tự động theo khuyến mãi hiện hành (nếu có) trên SẢN PHẨM (không áp dụng cho topping)
            int percent = 0;
            try {
                Integer p = khuyenMaiSanPhamRepository.findActiveDiscountPercentForProduct(bt.getMaSP(), java.time.LocalDate.now());
                if (p != null) percent = Math.max(0, Math.min(100, p));
            } catch (Exception ex) {
                log.warn("Không thể lấy khuyến mãi cho MaSP={}: {}", bt.getMaSP(), ex.getMessage());
            }

            BigDecimal giamGiaDong;
            if (percent > 0) {
                BigDecimal perUnitDiscount = donGia.multiply(BigDecimal.valueOf(percent)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                giamGiaDong = perUnitDiscount.multiply(BigDecimal.valueOf(item.getSoLuong())).setScale(2, RoundingMode.HALF_UP);
            } else {
                giamGiaDong = nz(item.getGiamGiaDong()).setScale(2, RoundingMode.HALF_UP);
            }
            BigDecimal soLuong = BigDecimal.valueOf(item.getSoLuong());
            BigDecimal lineSubtotal = donGia.add(toppingPerUnit).multiply(soLuong);
            BigDecimal thanhTien = lineSubtotal.subtract(giamGiaDong);
            if (thanhTien.compareTo(BigDecimal.ZERO) < 0) {
                thanhTien = BigDecimal.ZERO;
            }

            CTDonHang ct = CTDonHang.builder()
                    .donHang(dh)
                    .maBT(item.getMaBT())
                    .soLuong(item.getSoLuong())
                    .donGia(donGia)
                    .giamGiaDong(giamGiaDong)
                    .thanhTien(thanhTien)
                    .ghiChu(item.getGhiChu())
                    .build();
            ct = ctDonHangRepository.save(ct);

            // Save toppings breakdown per line
            if (toppings != null) {
                for (CreateOrderItemTopping t : toppings) {
                    var top = toppingRepository.findById(t.getMaTopping()).orElseThrow();
                    BigDecimal donGiaTop = top.getGiaThem().setScale(2, RoundingMode.HALF_UP);
                    // Topping total is per line: per-drink qty * line qty
                    BigDecimal thanhTienTop = donGiaTop
                            .multiply(BigDecimal.valueOf(t.getSoLuong()))
                            .multiply(BigDecimal.valueOf(item.getSoLuong()))
                            .setScale(2, RoundingMode.HALF_UP);

                    CTDonHangTopping row = CTDonHangTopping.builder()
                            .id(new CTDonHangToppingId(ct.getMaCT(), t.getMaTopping()))
                            .chiTietDonHang(ct)
                            .soLuong(t.getSoLuong())
                            .donGia(donGiaTop)
                            .thanhTien(thanhTienTop)
                            .build();
                    ctDonHangToppingRepository.save(row);
                }
            }

            tongHang = tongHang.add(thanhTien);
        }

        dh.setTongHang(tongHang.setScale(2, RoundingMode.HALF_UP));
        BigDecimal tongThanhToan = dh.getTongHang().subtract(dh.getGiamGiaDon()).add(dh.getPhiVanChuyen());
        if (tongThanhToan.compareTo(BigDecimal.ZERO) < 0) {
            tongThanhToan = BigDecimal.ZERO;
        }
        dh.setTongThanhToan(tongThanhToan.setScale(2, RoundingMode.HALF_UP));

        // Update order totals and flush to DB
        dh = donHangRepository.saveAndFlush(dh);

        log.info("Created order MaDH={} TongHang={} TongThanhToan={} by MaNV={}", dh.getMaDH(), dh.getTongHang(), dh.getTongThanhToan(), maNV);
        return dh;
    }
}
