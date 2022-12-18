package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.model.dto.ProductReceiptDto;
import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.model.entity.Product;
import com.example.test_task_clevertec.service.CardService;
import com.example.test_task_clevertec.service.ProductParser;
import com.example.test_task_clevertec.service.ProductService;
import com.example.test_task_clevertec.service.ReceiptDataValidatorService;
import com.example.test_task_clevertec.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ProductService productService;
    private final CardService cardService;
    private final ReceiptDataValidatorService receiptDataValidatorService;
    private final ProductParser productParser;

    @Override
    public ReceiptResponseDto generateReceipt(List<String> items, String cardNumber) {
        receiptDataValidatorService.validate(items, cardNumber);
        final Map<Long, Integer> productItems = productParser.parse(items);
        Integer discountValue = cardNumber == null
                ? 0
                : cardService.findByCardNumber(cardNumber).getDiscount();

        final List<ProductReceiptDto> productReceiptDtoList = new ArrayList<>();

        for (Map.Entry<Long, Integer> productItem : productItems.entrySet()) {
            Product product = productService.findById(productItem.getKey());
            ProductReceiptDto productReceipt = ProductReceiptDto.builder()
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .quantity(productItem.getValue())
                    .total(getTotalPrice(product.getPrice(), productItem.getValue(), product.isDiscounted()))
                    .build();
            productReceiptDtoList.add(productReceipt);
        }

        final BigDecimal totalSum = productReceiptDtoList
                .stream()
                .map(ProductReceiptDto::getTotal)
                .reduce(BigDecimal::add)
                .get();

        final BigDecimal totalDiscount = totalSum
                .multiply(BigDecimal.valueOf(discountValue))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        final BigDecimal total = totalSum.subtract(totalDiscount);

        return ReceiptResponseDto.builder()
                .receiptNumber("random_receipt_name")
                .products(productReceiptDtoList)
                .totalSum(totalSum)
                .totalDiscount(discountValue)
                .total(total)
                .build();
    }

    private BigDecimal getTotalPrice(BigDecimal price, Integer quantity, boolean isDiscounted) {
        final BigDecimal discount = isDiscounted && quantity > 5
                ? BigDecimal.valueOf(10)
                : BigDecimal.ZERO;

        final BigDecimal discountSum = price
                .multiply(BigDecimal.valueOf(quantity))
                .multiply(discount)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return price
                .multiply(BigDecimal.valueOf(quantity))
                .subtract(discountSum);
    }
}
