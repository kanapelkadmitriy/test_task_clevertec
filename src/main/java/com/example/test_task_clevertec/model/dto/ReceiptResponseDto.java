package com.example.test_task_clevertec.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ReceiptResponseDto {

    private String receiptNumber;
    private List<ProductReceiptDto> products;
    private BigDecimal totalSum;
    private Integer totalDiscount;
    private BigDecimal total;
}
