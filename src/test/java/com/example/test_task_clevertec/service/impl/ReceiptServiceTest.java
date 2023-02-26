package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.model.entity.Card;
import com.example.test_task_clevertec.model.entity.Product;
import com.example.test_task_clevertec.service.CardService;
import com.example.test_task_clevertec.service.ProductParser;
import com.example.test_task_clevertec.service.ProductService;
import com.example.test_task_clevertec.service.ReceiptDataValidatorService;
import com.example.test_task_clevertec.service.ReceiptWriterService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReceiptServiceTest {

    @InjectMocks
    ReceiptServiceImpl receiptService;
    @Mock
    ReceiptDataValidatorService receiptDataValidatorService;
    @Mock
    ProductParser productParser;
    @Mock
    CardService cardService;
    @Mock
    ProductService productService;
    @Mock
    ReceiptWriterService writerService;
    @Captor
    ArgumentCaptor<List<String>> itemsCaptor;
    @Captor
    ArgumentCaptor<ReceiptResponseDto> receiptCaptor;


    @ParameterizedTest
    @MethodSource("provideData")
    void generateReceiptTest(List<String> items,
                             String cardNumber,
                             Map<Long, Integer> parsedItems,
                             Card card,
                             List<Product> products,
                             ReceiptResponseDto mock) {
        doNothing().when(receiptDataValidatorService).validate(anyList(), any());
        when(productParser.parse(items)).thenReturn(parsedItems);
        when(cardService.findByCardNumber(cardNumber)).thenReturn(card);
        when(productService.findById(parsedItems.keySet().stream().toList().get(0))).thenReturn(products.get(0));
        when(productService.findById(parsedItems.keySet().stream().toList().get(1))).thenReturn(products.get(1));
        doNothing().when(writerService).write(any());

        final ReceiptResponseDto actual = receiptService.generateReceipt(items, cardNumber);

        assertEquals(mock.getTotal(), actual.getTotal());
        assertEquals(mock.getTotalSum(), actual.getTotalSum());
        assertEquals(mock.getTotalDiscount(), actual.getTotalDiscount());
        assertEquals(mock.getDiscountSum(), actual.getDiscountSum());

        verify(productParser).parse(itemsCaptor.capture());
        List<String> itemList = itemsCaptor.getValue();
        assertEquals(itemList, items);

        verify(writerService).write(receiptCaptor.capture());
        ReceiptResponseDto receiptResponseDto = receiptCaptor.getValue();
        assertEquals(mock.getTotal(), receiptResponseDto.getTotal());
        assertEquals(mock.getTotalSum(), receiptResponseDto.getTotalSum());
        assertEquals(mock.getTotalDiscount(), receiptResponseDto.getTotalDiscount());
        assertEquals(mock.getDiscountSum(), receiptResponseDto.getDiscountSum());

    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(List.of("1-3", "2-9"),
                        "card-1234",
                        Map.of(1L, 3, 2L, 9),
                        Card.builder()
                                .number("card-1234")
                                .discount(10)
                                .build(),
                        List.of(Product.builder()
                                        .id(1L)
                                        .description("product_1")
                                        .isDiscounted(true)
                                        .quantity(123)
                                        .price(BigDecimal.valueOf(145.16))
                                        .build(),
                                Product.builder()
                                        .id(2L)
                                        .description("product_2")
                                        .isDiscounted(true)
                                        .quantity(234)
                                        .price(BigDecimal.valueOf(212.52))
                                        .build()),
                        ReceiptResponseDto.builder()
                                .total(BigDecimal.valueOf(1632.02))
                                .totalSum(BigDecimal.valueOf(1813.36))
                                .totalDiscount(10)
                                .discountSum(BigDecimal.valueOf(181.34))
                                .build()),
                Arguments.of(List.of("1-7", "2-12"),
                        "card-1234",
                        Map.of(1L, 7, 2L, 12),
                        Card.builder()
                                .number("card-1234")
                                .discount(8)
                                .build(),
                        List.of(Product.builder()
                                        .id(1L)
                                        .description("product_3")
                                        .isDiscounted(true)
                                        .quantity(500)
                                        .price(BigDecimal.valueOf(121.19))
                                        .build(),
                                Product.builder()
                                        .id(2L)
                                        .description("product_4")
                                        .isDiscounted(false)
                                        .quantity(600)
                                        .price(BigDecimal.valueOf(345.28))
                                        .build()),
                        ReceiptResponseDto.builder()
                                .total(BigDecimal.valueOf(3427.75))
                                .totalSum(BigDecimal.valueOf(3725.81))
                                .totalDiscount(8)
                                .discountSum(BigDecimal.valueOf(298.06))
                                .build()
                )
        );
    }
}
