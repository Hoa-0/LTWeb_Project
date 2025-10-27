package com.alotra.web.dto.admin;

import com.alotra.web.entity.DanhMucSanPham;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryAdminViewDTO {
    private Integer maDM;
    private String tenDM;
    private String moTa;
    private boolean isDeleted; // Kiểm tra DeletedAt != null

    public CategoryAdminViewDTO(DanhMucSanPham dm) {
        this.maDM = dm.getMaDM();
        this.tenDM = dm.getTenDM();
        this.moTa = dm.getMoTa();
        this.isDeleted = dm.getDeletedAt() != null;
    }
}