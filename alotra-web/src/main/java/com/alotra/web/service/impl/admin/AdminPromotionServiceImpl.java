package com.alotra.web.service.impl.admin;

import com.alotra.web.dto.admin.PromotionAdminViewDTO;
import com.alotra.web.dto.admin.PromotionFormDTO;
import com.alotra.web.entity.SuKienKhuyenMai;
import com.alotra.web.repository.SuKienKhuyenMaiRepository;
import com.alotra.web.service.admin.AdminPromotionService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdminPromotionServiceImpl implements AdminPromotionService {

    @Autowired
    private SuKienKhuyenMaiRepository promotionRepository;

    private SuKienKhuyenMai findPromotionById(Integer id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khuyến mãi với ID: " + id));
    }

    // === PHẦN LIST VIEW (Đã có) ===

    @Override
    @Transactional(readOnly = true)
    public Page<PromotionAdminViewDTO> findAllPaginated(Pageable pageable) {
        Page<SuKienKhuyenMai> entityPage = promotionRepository.findAll(pageable);
        return entityPage.map(PromotionAdminViewDTO::fromEntity);
    }

    @Override
    @Transactional
    public void toggleStatus(Integer id) {
        SuKienKhuyenMai promotion = findPromotionById(id);
        if (promotion.getDeletedAt() != null) {
            throw new IllegalStateException("Không thể thay đổi trạng thái của khuyến mãi đã bị xóa.");
        }
        byte newStatus = (promotion.getTrangThai() != null && promotion.getTrangThai() == 1) ? (byte) 0 : (byte) 1;
        promotion.setTrangThai(newStatus);
        promotionRepository.save(promotion);
    }

    @Override
    @Transactional
    public void softDelete(Integer id) {
        SuKienKhuyenMai promotion = findPromotionById(id);
        if (promotion.getDeletedAt() == null) {
            promotion.softDelete(); // Dùng hàm softDelete() của Entity
            promotionRepository.save(promotion);
        }
    }

    @Override
    @Transactional
    public void restore(Integer id) {
        SuKienKhuyenMai promotion = findPromotionById(id);
        if (promotion.getDeletedAt() != null) {
            promotion.setDeletedAt(null);
            promotion.setTrangThai((byte) 1);
            promotionRepository.save(promotion);
        }
    }

    @Override
    @Transactional
    public void deletePermanently(Integer id) {
        SuKienKhuyenMai promotion = findPromotionById(id);
        promotionRepository.delete(promotion);
    }

    // === PHẦN FORM (Thêm/Sửa) ===

    @Override
    @Transactional(readOnly = true)
    public PromotionFormDTO findDtoById(Integer id) {
        SuKienKhuyenMai entity = findPromotionById(id);
        return PromotionFormDTO.fromEntity(entity);
    }

    @Override
    @Transactional
    public void save(PromotionFormDTO dto) {
        SuKienKhuyenMai entity;

        if (dto.getMaKM() == null) {
            // 1. THÊM MỚI
            entity = new SuKienKhuyenMai();
            // Đặt các giá trị mặc định nếu cần (ví dụ: LuotXem)
            entity.setLuotXem(0); 
        } else {
            // 2. CẬP NHẬT
            entity = findPromotionById(dto.getMaKM());
        }

        // 3. MAP DỮ LIỆU TỪ DTO SANG ENTITY
        entity.setTenSuKien(dto.getTenSuKien());
        entity.setMoTa(dto.getMoTa());
        entity.setNgayBD(dto.getNgayBD());
        entity.setNgayKT(dto.getNgayKT());
        entity.setTrangThai(dto.getTrangThai());
        
        // Tạm thời gán UrlAnh trực tiếp
        // Khi có Cloudinary, logic upload file sẽ ở đây
        entity.setUrlAnh(dto.getUrlAnh()); 
        
        // 4. LƯU
        promotionRepository.save(entity);
    }
}