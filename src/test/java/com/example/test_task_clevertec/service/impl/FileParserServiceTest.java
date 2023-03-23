package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.model.dto.ReceiptRequestDto;
import com.example.test_task_clevertec.model.entity.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FileParserServiceTest {

    @InjectMocks
    TxtFileParserServiceImpl parserService;

    @ParameterizedTest
    @MethodSource("provideValidMultipartData")
    void successfulParse(MultipartFile file, ReceiptRequestDto mock) {
        ReceiptRequestDto actual = parserService.parse(file);
        assertEquals(mock.getCardNumber(),actual.getCardNumber());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidMultipartData")
    void unsuccessfulParse(MultipartFile file) {
        assertThrows(BusinessException.class, () -> parserService.parse(file));
    }

    private static Stream<Arguments> provideValidMultipartData() {
        String root = "src/test/java/com/example/test_task_clevertec/data";
        byte[] content1 = null;
        byte[] content2 = null;
        byte[] content3 = null;
        try {
            content1 = Files.readAllBytes(Paths.get(root +"/receipt_valid_data_1.txt"));
            content2 = Files.readAllBytes(Paths.get(root +"/receipt_valid_data_2.txt"));
            content3 = Files.readAllBytes(Paths.get(root +"/receipt_valid_data_3.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Stream.of(
                Arguments.of(new MockMultipartFile("receipt.txt",
                        "receipt.txt",
                        "text/plain",
                        content1),
                        ReceiptRequestDto.builder()
                                .items(List.of("1-10","2-10"))
                                .cardNumber("card-1234")
                                .build()),
                Arguments.of(new MockMultipartFile("receipt.txt",
                                "receipt.txt",
                                "text/plain",
                                content2),
                        ReceiptRequestDto.builder()
                                .items(List.of("5-5","7-8","9-10"))
                                .cardNumber("card-1234")
                                .build()),
                Arguments.of(new MockMultipartFile("receipt.txt",
                                "receipt.txt",
                                "text/plain",
                                content3),
                        ReceiptRequestDto.builder()
                                .items(List.of("1-1"))
                                .cardNumber("CARD-1234")
                                .build())
        );
    }

    private static Stream<Arguments> provideInvalidMultipartData() {
        String root = "src/test/java/com/example/test_task_clevertec/data";
        byte[] content1 = null;
        byte[] content2 = null;
        byte[] content3 = null;
        try {
            content1 = Files.readAllBytes(Paths.get(root +"/receipt_invalid_data_1.txt"));
            content2 = Files.readAllBytes(Paths.get(root +"/receipt_invalid_data_2.txt"));
            content3 = Files.readAllBytes(Paths.get(root +"/receipt_invalid_data_3.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Stream.of(
                Arguments.of(new MockMultipartFile("receipt.txt",
                                "receipt.txt",
                                "text/plain",
                                content1)),
                Arguments.of(new MockMultipartFile("receipt.txt",
                                "receipt.txt",
                                "text/plain",
                                content2)),
                Arguments.of(new MockMultipartFile("receipt.txt",
                                "receipt.txt",
                                "text/plain",
                                content3))
        );
    }
}
