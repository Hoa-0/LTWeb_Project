package com.alotra.web.service.impl;

import com.alotra.web.entity.KhuyenMaiSanPham;
import com.alotra.web.entity.SuKienKhuyenMai;
import com.alotra.web.repository.KhuyenMaiSanPhamRepository;
import com.alotra.web.repository.SuKienKhuyenMaiRepository;
import com.alotra.web.service.KhuyenMaiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    private final SuKienKhuyenMaiRepository suKienRepo;
    private final KhuyenMaiSanPhamRepository kmspRepo;

    @Override
    @Transactional(readOnly = true)
    public Page<SuKienKhuyenMai> listKhuyenMai(Pageable pageable) {
        return suKienRepo.findAllNotDeleted(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long countAll() {
        return suKienRepo.countAllNotDeleted();
    }

    @Override
    @Transactional(readOnly = true)
    public long countActive() {
        return suKienRepo.countActive(LocalDate.now());
    }

    @Override
    @Transactional(readOnly = true)
    public long countExpired() {
        return suKienRepo.countExpired(LocalDate.now());
    }

    @Override
    @Transactional(readOnly = true)
    public long countExpiringSoon(int days) {
        LocalDate today = LocalDate.now();
        return suKienRepo.countExpiringSoon(today, today.plusDays(days));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SuKienKhuyenMai> getById(Integer maKM) {
        return suKienRepo.findById(maKM).filter(km -> km.getDeletedAt() == null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KhuyenMaiSanPham> getProductsByEvent(Integer maKM) {
        return kmspRepo.findByMaKM(maKM);
    }

    @Override
    public SuKienKhuyenMai save(SuKienKhuyenMai suKien) {
        if (suKien.getTrangThai() == null) suKien.setTrangThai((byte)1);
        if (suKien.getLuotXem() == null) suKien.setLuotXem(0);
        return suKienRepo.save(suKien);
    }

    @Override
    public SuKienKhuyenMai update(Integer maKM, SuKienKhuyenMai suKien) {
        SuKienKhuyenMai existing = getById(maKM).orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
        existing.setTenSuKien(suKien.getTenSuKien());
        existing.setMoTa(suKien.getMoTa());
        existing.setNgayBD(suKien.getNgayBD());
        existing.setNgayKT(suKien.getNgayKT());
        existing.setTrangThai(suKien.getTrangThai());
        existing.setUrlAnh(suKien.getUrlAnh());
        return suKienRepo.save(existing);
    }

    @Override
    public void softDelete(Integer maKM) {
        SuKienKhuyenMai existing = getById(maKM).orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
        existing.setDeletedAt(LocalDateTime.now());
        existing.setTrangThai((byte)0);
        suKienRepo.save(existing);
    }

    @Override
    public void replaceProductsForEvent(Integer maKM, Map<Integer, Integer> productPercents) {
        // Xác thực sự kiện tồn tại và chưa bị xóa
    getById(maKM).orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));

        // Xóa cấu hình cũ
        kmspRepo.deleteByMaKM(maKM);

        if (productPercents == null || productPercents.isEmpty()) {
            return; // Không có gì để thêm
        }

        // Thêm mới các cấu hình hợp lệ (1..100)
        for (Map.Entry<Integer, Integer> e : productPercents.entrySet()) {
            Integer maSP = e.getKey();
            Integer percent = e.getValue();
            if (maSP == null || percent == null) continue;
            int p = Math.max(0, Math.min(100, percent));
            if (p <= 0) continue; // Bỏ qua 0%

            com.alotra.web.entity.KhuyenMaiSanPham row = new com.alotra.web.entity.KhuyenMaiSanPham();
            row.setMaKM(maKM);
            row.setMaSP(maSP);
            row.setPhanTramGiam(p);
            kmspRepo.save(row);
        }
        log.info("Updated {} product discounts for event MaKM={}", productPercents.size(), maKM);
    }
}
