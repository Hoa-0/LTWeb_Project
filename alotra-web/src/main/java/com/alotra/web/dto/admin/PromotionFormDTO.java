package com.alotra.web.dto.admin;

import com.alotra.web.entity.SuKienKhuyenMai;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * DTO này dùng riêng cho việc nhận và validate dữ liệu
 * từ form Thêm/Sửa Khuyến mãi.
 */
@Data
@NoArgsConstructor
public class PromotionFormDTO {

    private Integer maKM; // Sẽ là null khi Thêm mới

    @NotBlank(message = "Tên sự kiện không được để trống")
    private String tenSuKien;

    private String moTa;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayBD;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @FutureOrPresent(message = "Ngày kết thúc phải là ngày hiện tại hoặc tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayKT;

    @NotNull(message = "Vui lòng chọn trạng thái")
    private Byte trangThai; // 1: Active, 0: Inactive

    // Tạm thời chấp nhận UrlAnh là một chuỗi.
    // Khi tích hợp Cloudinary, trường này sẽ đổi thành MultipartFile
    private String urlAnh;

    // Custom validation: Ngày kết thúc phải sau hoặc bằng ngày bắt đầu
    @AssertTrue(message = "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu")
    public boolean isNgayKTValid() {
        return ngayBD == null || ngayKT == null || !ngayKT.isBefore(ngayBD);
    }

    /**
     * Chuyển đổi từ Entity sang DTO để đổ dữ liệu vào form (lúc Edit)
     */
    public static PromotionFormDTO fromEntity(SuKienKhuyenMai entity) {
        PromotionFormDTO dto = new PromotionFormDTO();
        dto.setMaKM(entity.getMaKM());
        dto.setTenSuKien(entity.getTenSuKien());
        dto.setMoTa(entity.getMoTa());
        dto.setNgayBD(entity.getNgayBD());
        dto.setNgayKT(entity.getNgayKT());
        dto.setTrangThai(entity.getTrangThai());
        dto.setUrlAnh(entity.getUrlAnh());
        return dto;
    }
}