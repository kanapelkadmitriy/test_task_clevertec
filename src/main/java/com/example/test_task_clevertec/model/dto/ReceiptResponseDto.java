package com.example.test_task_clevertec.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReceiptResponseDto {

    private String receiptNumber;
    private List<ProductReceiptDto> products;
}
