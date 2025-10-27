package com.alotra.web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List; // Import nếu cần dùng List cho KhuyenMaiSanPham
//Annotation @AssertTrue cần import:
import jakarta.validation.constraints.AssertTrue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SuKienKhuyenMai")
public class SuKienKhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKM")
    private Integer maKM;

    @NotBlank(message = "Tên sự kiện không được để trống")
    @Column(name = "TenSuKien", nullable = false, length = 255)
    private String tenSuKien;

    @Column(name = "MoTa", length = 255)
    private String moTa;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Định dạng khi nhận từ form
    @Column(name = "NgayBD", nullable = false)
    private LocalDate ngayBD;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @FutureOrPresent(message = "Ngày kết thúc phải là ngày hiện tại hoặc tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "NgayKT", nullable = false)
    private LocalDate ngayKT;

    @NotNull // Đảm bảo trạng thái không null
    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai; // 1: Active, 0: Inactive

    @Column(name = "UrlAnh", columnDefinition = "NVARCHAR(MAX)")
    private String urlAnh;

    @Column(name = "LuotXem")
    private Integer luotXem;

    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

    // Optional: Liên kết OneToMany đến KhuyenMaiSanPham nếu cần quản lý chi tiết
    // @OneToMany(mappedBy = "suKienKhuyenMai", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<KhuyenMaiSanPham> khuyenMaiSanPhams;

    // Helper methods
    public boolean isActive() {
        return trangThai != null && trangThai == 1 && deletedAt == null;
    }

    public void setActive(boolean active) {
        this.trangThai = active ? (byte) 1 : (byte) 0;
        if (!active) {
            this.deletedAt = LocalDateTime.now(); // Có thể soft delete khi deactive
        } else {
            this.deletedAt = null;
        }
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        this.trangThai = (byte) 0;
    }

    // Custom validation: Ngày kết thúc phải sau hoặc bằng ngày bắt đầu
    @AssertTrue(message = "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu")
    public boolean isNgayKTValid() {
        return ngayBD == null || ngayKT == null || !ngayKT.isBefore(ngayBD);
    }
}

