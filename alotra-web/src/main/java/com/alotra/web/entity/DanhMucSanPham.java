package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DanhMucSanPham")
public class DanhMucSanPham {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDM")
    private Integer maDM;
    
    @Column(name = "TenDM", nullable = false, length = 255)
    private String tenDM;
    
    @Column(name = "MoTa", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;
    
    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;
    
    // Relationships
    @OneToMany(mappedBy = "danhMuc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SanPham> sanPhams;
    
    // Helper methods
    public boolean isActive() {
        return deletedAt == null;
    }
    
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}