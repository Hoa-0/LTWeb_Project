package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Một danh mục có nhiều sản phẩm
    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}