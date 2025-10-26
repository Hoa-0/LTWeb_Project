package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SizeSanPham")
public class SizeSanPham {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSize")
    private Integer maSize;
    
    @Column(name = "TenSize", nullable = false, length = 10)
    private String tenSize;
    
    @Column(name = "TrangThai", nullable = false)
    private Byte trangThai; // 1: Active, 0: Inactive
    
    // Helper methods
    public boolean isActive() {
        return trangThai != null && trangThai == 1;
    }
    
    public void setActive(boolean active) {
        this.trangThai = active ? (byte) 1 : (byte) 0;
    }
}