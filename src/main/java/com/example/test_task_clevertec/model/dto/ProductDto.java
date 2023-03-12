package com.example.test_task_clevertec.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDto {

    private Long id;
    private String description;
    private BigDecimal price;
    private int quantity;
    private boolean isDiscounted;
    private String vendorCode;
}
