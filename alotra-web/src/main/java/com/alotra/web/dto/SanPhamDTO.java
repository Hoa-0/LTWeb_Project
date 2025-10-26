package com.alotra.web.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SanPhamDTO {
    private Integer maSP;
    private String tenSP;
    private String moTa;
    private BigDecimal giaSP;
    private String hinhAnh;
    private Byte trangThai;
    private Integer maDM;
    private String tenDanhMuc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<BienTheSanPhamDTO> bienTheSanPhams;
    private List<BienTheDTO> bienThes; // Alternative name for compatibility
    
    @Data
    public static class BienTheSanPhamDTO {
        private Integer maBT;
        private Integer maSP;
        private Integer maSize;
        private Integer maTopping;
        private BigDecimal giaBT;
        private Integer soLuongTon;
        private Byte trangThai;
        private String tenSize;
        private String tenTopping;
    }

    @Data
    public static class BienTheDTO {
        private Integer maBT;
        private Integer maSP;
        private Integer maSize;
        private Integer maTopping;
        private BigDecimal giaBT;
        private BigDecimal giaBan; // Alternative name
        private Integer soLuongTon;
        private Byte trangThai;
        private String tenSize;
        private String tenTopping;
        
        // Getter for compatibility
        public BigDecimal getGiaBan() {
            return giaBT != null ? giaBT : giaBan;
        }
    }

    // Getter for compatibility
    public List<BienTheDTO> getBienThes() {
        return bienThes;
    }

    public void setBienThes(List<BienTheDTO> bienThes) {
        this.bienThes = bienThes;
    }
    
    // Additional compatibility methods
    public String getAnhSanPham() {
        return hinhAnh;
    }
    
    public void setAnhSanPham(String anhSanPham) {
        this.hinhAnh = anhSanPham;
    }
    
    public String getUrlAnh() {
        return hinhAnh;
    }
    
    public void setUrlAnh(String urlAnh) {
        this.hinhAnh = urlAnh;
    }
}