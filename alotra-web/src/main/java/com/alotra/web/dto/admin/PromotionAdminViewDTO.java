package com.alotra.web.dto.admin;

import com.alotra.web.entity.SuKienKhuyenMai; // <-- Đã cập nhật package
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class PromotionAdminViewDTO {

    private Integer maKM;
    private String tenSuKien;
    private String ngayBD; // Đã format
    private String ngayKT; // Đã format
    private Byte trangThai; // <-- Đã cập nhật (int -> Byte)
    private LocalDateTime deletedAt;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static PromotionAdminViewDTO fromEntity(SuKienKhuyenMai entity) {
        return PromotionAdminViewDTO.builder()
                .maKM(entity.getMaKM())
                .tenSuKien(entity.getTenSuKien())
                .ngayBD(entity.getNgayBD().format(DATE_FORMATTER))
                .ngayKT(entity.getNgayKT().format(DATE_FORMATTER))
                .trangThai(entity.getTrangThai())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    // Các phương thức này được gọi từ file promotion-list.html
    
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public String getStatusText() {
        if (isDeleted()) {
            return "Đã xóa";
        }
        // Cập nhật kiểm tra (Byte)
        return (this.trangThai != null && this.trangThai == 1) ? "Đang hoạt động" : "Vô hiệu hóa";
    }

    public String getStatusBadgeClass() {
        if (isDeleted()) {
            return "bg-secondary";
        }
        // Cập nhật kiểm tra (Byte)
        return (this.trangThai != null && this.trangThai == 1) ? "bg-success" : "bg-warning";
    }
}