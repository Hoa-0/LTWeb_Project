package com.alotra.web.dto.revenue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueSummaryDTO {
    private LocalDate from;
    private LocalDate to;
    private long totalOrders;       // all orders in range
    private long paidOrders;        // orders with paid status in range
    private long deliveredOrders;   // delivered status in range
    private long cancelledOrders;   // cancelled status in range
    private BigDecimal revenuePaid; // sum TongThanhToan for paid orders
    private BigDecimal avgPaidOrderValue; // revenuePaid / paidOrders
}
