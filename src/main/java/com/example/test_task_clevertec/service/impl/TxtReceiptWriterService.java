package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.model.dto.ProductReceiptDto;
import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.service.ReceiptWriterService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.example.test_task_clevertec.util.Constants.RECEIPT_PATH;

@Service
@RequiredArgsConstructor
public class TxtReceiptWriterService implements ReceiptWriterService {
    private final ReceiptDataGenerator receiptDataGenerator;

    @Override
    public void write(ReceiptResponseDto receiptResponse) {
        final List<String> receiptData = receiptDataGenerator.generateReceiptData(receiptResponse);
        final Path rootPath = Path.of(RECEIPT_PATH);
        try {
            if (!Files.exists(rootPath)) {
                Files.createDirectories(rootPath);
            }
            final Path receiptPath = Path.of(RECEIPT_PATH + receiptResponse.getReceiptNumber() + ".txt");
            Files.write(receiptPath, receiptData);
        } catch (Exception ex) {
            throw new BusinessException("exception occurred during writing data in receipt");
        }
    }
}
