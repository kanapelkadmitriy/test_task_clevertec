package com.example.test_task_clevertec.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductReceiptDto {

    @JsonProperty("QTY")
    private int quantity;

    @JsonProperty("DESCRIPTION")
    private String description;

    @JsonProperty("PRICE")
    private BigDecimal price;

    @JsonProperty("TOTAL")
    private BigDecimal total;
}
