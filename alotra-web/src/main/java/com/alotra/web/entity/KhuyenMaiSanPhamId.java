package com.alotra.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhuyenMaiSanPhamId implements Serializable {
    private Integer maKM;
    private Integer maSP;
}
