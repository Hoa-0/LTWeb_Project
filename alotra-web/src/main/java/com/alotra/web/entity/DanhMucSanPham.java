package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "DanhMucSanPham")
public class DanhMucSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDM")
    private Integer maDM;

    @Column(name = "tenDM", nullable = false, length = 100)
    private String tenDM;

    @Column(name = "moTa", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "trangThai")
    private Byte trangThai = 1; // 1: Active, 0: Inactive

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "maDM", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SanPham> sanPhams;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}