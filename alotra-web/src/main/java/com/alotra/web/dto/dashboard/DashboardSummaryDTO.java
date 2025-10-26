package com.alotra.web.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDTO {
    private long totalProducts;
    private long todayOrders;
    private long pendingOrders;
    private BigDecimal monthlyRevenue;
}
