package com.alotra.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamDTO {
    
    private Integer maSP;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String tenSP;
    
    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự")
    private String moTa;
    
    @NotNull(message = "Vui lòng chọn danh mục")
    private Integer maDM;
    
    private String tenDanhMuc;
    
    private String urlAnh;
    
    // File upload cho ảnh sản phẩm
    private MultipartFile anhSanPham;
    
    private Byte trangThai = 1; // Mặc định là active
    
    // Thông tin biến thể sản phẩm
    private List<BienTheDTO> bienThes;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BienTheDTO {
        private Integer maBT;
        private Integer maSize;
        private String tenSize;
        
        @NotNull(message = "Giá bán không được để trống")
        @DecimalMin(value = "0.0", inclusive = false, message = "Giá bán phải lớn hơn 0")
        @Digits(integer = 8, fraction = 2, message = "Giá bán không hợp lệ")
        private BigDecimal giaBan;
        
        private Byte trangThai = 1;
        
        // Helper methods
        public boolean isActive() {
            return trangThai != null && trangThai == 1;
        }
        
        public void setActive(boolean active) {
            this.trangThai = active ? (byte) 1 : (byte) 0;
        }
    }
    
    // Helper methods
    public boolean isActive() {
        return trangThai != null && trangThai == 1;
    }
    
    public void setActive(boolean active) {
        this.trangThai = active ? (byte) 1 : (byte) 0;
    }
}