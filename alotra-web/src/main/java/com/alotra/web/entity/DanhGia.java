package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DanhGia")
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDG")
    private Integer maDG;

    @Column(name = "maKH")
    private Integer maKH;

    @Column(name = "maCT")  // Changed from maSP to maCT to match order line
    private Integer maCT;

    @Column(name = "maDH")
    private Integer maDH;

    @Column(name = "soSao")
    private Integer soSao;

    @Column(name = "noiDung", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "ngayDanhGia")
    private LocalDateTime ngayDanhGia;

    @Column(name = "trangThai")
    private Byte trangThai = 1; // 1: Active, 0: Hidden

    // Admin reply fields
    @Column(name = "TraLoiBoi", length = 255)
    private String traLoiBoi;

    @Column(name = "ThoiGianTraLoi")
    private LocalDateTime thoiGianTraLoi;

    @Column(name = "NguoiTraLoi", length = 255)
    private String nguoiTraLoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKH", insertable = false, updatable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maCT", insertable = false, updatable = false)
    private CTDonHang ctDonHang;

    @PrePersist
    protected void onCreate() {
        ngayDanhGia = LocalDateTime.now();
    }

    // Compatibility methods
    public Integer getId() {
        return maDG;
    }

    public KhachHang getCustomer() {
        return khachHang;
    }

    public void setCustomer(KhachHang customer) {
        this.khachHang = customer;
        if (customer != null) {
            this.maKH = customer.getMaKH();
        }
    }

    public CTDonHang getOrderLine() {
        return ctDonHang;
    }

    public void setOrderLine(CTDonHang orderLine) {
        this.ctDonHang = orderLine;
        if (orderLine != null) {
            this.maCT = orderLine.getMaCT();
        }
    }

    public int getStars() {
        return soSao != null ? soSao : 0;
    }

    public void setStars(int stars) {
        this.soSao = stars;
    }

    public String getComment() {
        return noiDung;
    }

    public void setComment(String comment) {
        this.noiDung = comment;
    }

    public LocalDateTime getCreatedAt() {
        return ngayDanhGia;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.ngayDanhGia = createdAt;
    }

    // Admin reply methods
    public void setAdminReply(String reply) {
        this.traLoiBoi = reply;
    }

    public void setAdminRepliedAt(LocalDateTime repliedAt) {
        this.thoiGianTraLoi = repliedAt;
    }

    public void setAdminRepliedBy(String repliedBy) {
        this.nguoiTraLoi = repliedBy;
    }

    public String getAdminReply() {
        return traLoiBoi;
    }

    public LocalDateTime getAdminRepliedAt() {
        return thoiGianTraLoi;
    }

    public String getAdminRepliedBy() {
        return nguoiTraLoi;
    }
}