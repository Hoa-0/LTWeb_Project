package com.alotra.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemTopping {
    private Integer maTopping;
    private Integer soLuong; // per drink
}
