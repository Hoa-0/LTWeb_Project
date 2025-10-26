package com.alotra.web.entity;

import jakarta.persistence.*;
<<<<<<< HEAD

@Entity
@Table(name = "SizeSanPham")
public class SizeSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSize")
    private Integer id;

    @Column(name = "TenSize", nullable = false, unique = true, length = 10)
    private String name;

    @Column(name = "TrangThai", nullable = false)
    private Integer status;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
=======
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
>>>>>>> e66511f5f76c02a6c7a92993afb90a3d8655037c
