package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maReview")
    private Integer maReview;

    @Column(name = "maKH")
    private Integer maKH;

    @Column(name = "maSP")
    private Integer maSP;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKH", insertable = false, updatable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maSP", insertable = false, updatable = false)
    private SanPham sanPham;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Compatibility methods
    public Integer getId() {
        return maReview;
    }

    public void setId(Integer id) {
        this.maReview = id;
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

    public SanPham getProduct() {
        return sanPham;
    }

    public void setProduct(SanPham product) {
        this.sanPham = product;
        if (product != null) {
            this.maSP = product.getMaSP();
        }
    }
}