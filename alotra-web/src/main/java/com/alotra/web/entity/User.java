package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;   // Tên đăng nhập (unique)

    @Column(nullable = false)
    private String fullName;   // Họ và tên (dùng trong form đăng ký)

    @Column(nullable = false, unique = true)
    private String email;      // Email (unique)

    @Column(nullable = false)
    private String password;   // Mật khẩu (mã hoá bằng BCrypt)

    @Column(nullable = false)
    private String role;       // ROLE_USER, ROLE_VENDOR, ROLE_ADMIN,...

    // Trường dành cho người bán (vendor)
    private String shopName;      // Tên cửa hàng
    private String shopAddress;   // Địa chỉ cửa hàng
    private boolean shopActive = false; // Shop chưa được admin duyệt mặc định

    // Timestamp fields
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

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
