package com.alotra.web.dto.admin;

import com.alotra.web.entity.KhachHang; // Import KhachHang entity
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerAdminViewDTO {
    private Integer maKH;
    private String username; // Thêm username nếu cần hiển thị
    private String tenKH;
    private String email;
    private String soDienThoai;
    private Byte trangThai; // Giữ byte để xử lý logic
    // Không có isDeleted vì KhachHang không có DeletedAt

    // Constructor để chuyển đổi từ Entity
    public CustomerAdminViewDTO(KhachHang kh) {
        this.maKH = kh.getMaKH();
        // Giả sử KhachHang entity có trường username (nếu không có thì bỏ dòng này)
        // this.username = kh.getUsername();
        this.tenKH = kh.getTenKH();
        this.email = kh.getEmail();
        this.soDienThoai = kh.getSoDienThoai();
        this.trangThai = kh.getTrangThai();
    }
}