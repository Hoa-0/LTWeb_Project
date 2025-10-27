package com.alotra.web.dto.admin;

import com.alotra.web.entity.NhanVien;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeAdminViewDTO {
    private Integer maNV;
    private String username;
    private String email;
    private String tenNV;
    private Byte vaiTro;
    private String vaiTroText;
    private Byte trangThai;
    private String soDienThoai;
    private String anhDaiDien;
    private boolean isDeleted; // Kiểm tra DeletedAt != null

    public EmployeeAdminViewDTO(NhanVien nv) {
        this.maNV = nv.getMaNV();
        this.username = nv.getUsername();
        this.email = nv.getEmail();
        this.tenNV = nv.getTenNV();
        this.vaiTro = nv.getVaiTro();
        this.vaiTroText = nv.getVaiTroText();
        this.trangThai = nv.getTrangThai();
        this.soDienThoai = nv.getSoDienThoai();
        this.anhDaiDien = nv.getAnhDaiDien();
        this.isDeleted = nv.getDeletedAt() != null;
    }
}