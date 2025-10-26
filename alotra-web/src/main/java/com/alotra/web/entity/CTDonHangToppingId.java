package com.alotra.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTDonHangToppingId implements Serializable {
    @Column(name = "MaCT")
    private Integer maCT;

    @Column(name = "MaTopping")
    private Integer maTopping;
}
