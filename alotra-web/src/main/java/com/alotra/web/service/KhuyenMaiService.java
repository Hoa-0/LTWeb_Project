package com.alotra.web.service;

import com.alotra.web.entity.KhuyenMaiSanPham;
import com.alotra.web.entity.SuKienKhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KhuyenMaiService {
    Page<SuKienKhuyenMai> listKhuyenMai(Pageable pageable);
    long countAll();
    long countActive();
    long countExpired();
    long countExpiringSoon(int days);

    Optional<SuKienKhuyenMai> getById(Integer maKM);
    List<KhuyenMaiSanPham> getProductsByEvent(Integer maKM);

    SuKienKhuyenMai save(SuKienKhuyenMai suKien);
    SuKienKhuyenMai update(Integer maKM, SuKienKhuyenMai suKien);
    void softDelete(Integer maKM);

    // Thay thế toàn bộ danh sách sản phẩm áp dụng cho sự kiện cùng với phần trăm giảm
    void replaceProductsForEvent(Integer maKM, Map<Integer, Integer> productPercents);
}
