package com.alotra.web.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRevenueDTO {
    private String tenDM;
    private BigDecimal totalRevenue;
}