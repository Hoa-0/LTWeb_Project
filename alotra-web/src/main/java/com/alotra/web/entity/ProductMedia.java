package com.alotra.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ProductMedia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IsPrimary")
    private Boolean isPrimary;

    @Column(name = "Url", length = 255, nullable = false)
    private String url;

    @Column(name = "ProductId", nullable = false)
    private Integer productId;
}
