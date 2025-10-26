package com.alotra.web.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentOrderDTO {
    private Integer maDH;
    private String tenNguoiNhan;
    private BigDecimal tongThanhToan;
    private String trangThai;
    private LocalDateTime ngayLap;
}
