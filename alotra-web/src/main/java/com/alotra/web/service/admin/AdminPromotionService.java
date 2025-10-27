package com.alotra.web.service.admin;

import com.alotra.web.dto.admin.PromotionAdminViewDTO;
import com.alotra.web.dto.admin.PromotionFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPromotionService {

    // === PHẦN LIST VIEW (Đã có) ===
    Page<PromotionAdminViewDTO> findAllPaginated(Pageable pageable);
    void toggleStatus(Integer id);
    void softDelete(Integer id);
    void restore(Integer id);
    void deletePermanently(Integer id);

    // === PHẦN FORM (Thêm/Sửa) ===

    /**
     * Lấy thông tin khuyến mãi dưới dạng DTO để hiển thị trên form Sửa.
     * @param id Mã KM
     * @return DTO chứa dữ liệu
     */
    PromotionFormDTO findDtoById(Integer id);

    /**
     * Lưu (Thêm mới hoặc Cập nhật) một khuyến mãi.
     * Tự động nhận diện Thêm mới (nếu DTO.maKM == null) 
     * hoặc Cập nhật (nếu DTO.maKM != null).
     * @param dto DTO từ form
     */
    void save(PromotionFormDTO dto);
}