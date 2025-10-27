package com.alotra.web.dto.admin;

import com.alotra.web.entity.SanPham;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductAdminViewDTO {
    private Integer maSP;
    private String tenSP;
    private String tenDM; // Lấy tên danh mục
    private Byte trangThai;
    private String urlAnh; // Để hiển thị ảnh thumbnail
    private boolean isDeleted; // Kiểm tra DeletedAt != null

    // Constructor chuyển đổi từ Entity SanPham
    public ProductAdminViewDTO(SanPham sp) {
        this.maSP = sp.getMaSP();
        this.tenSP = sp.getTenSP();
        // Lấy tên Danh mục qua relationship (cần đảm bảo EAGER fetch hoặc xử lý lazy loading)
        if (sp.getDanhMuc() != null) {
            this.tenDM = sp.getDanhMuc().getTenDM();
        } else {
            this.tenDM = "N/A"; // Hoặc báo lỗi nếu danh mục là bắt buộc
        }
        this.trangThai = sp.getTrangThai();
        this.urlAnh = sp.getUrlAnh();
        this.isDeleted = sp.getDeletedAt() != null;
    }
}