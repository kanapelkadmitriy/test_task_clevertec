package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.model.dto.ReceiptRequestDto;
import com.example.test_task_clevertec.service.FileParserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.test_task_clevertec.service.impl.ReceiptDataValidatorServiceImpl.CARD_PATTERN;

@Service
public class TxtFileParserServiceImpl implements FileParserService {
    public static final Charset WINDOWS_CHARSET = Charset.forName("WINDOWS-1251");

    @Override
    public ReceiptRequestDto parse(MultipartFile file) {
        final List<String> lines = readDataFromFile(file);
        final List<String> items = new ArrayList<>();
        String cardNumber = null;

        for (String line : lines) {
            if (CARD_PATTERN.matcher(line).matches()) {
                if (cardNumber != null && !cardNumber.equals(line)) {
                    throw new BusinessException("was added more than one card");
                }
                cardNumber = line;
                continue;
            }
            items.add(line);
        }
        return ReceiptRequestDto.builder()
                .items(items)
                .cardNumber(cardNumber)
                .build();
    }

    private List<String> readDataFromFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), WINDOWS_CHARSET))) {
            return reader.lines()
                    .filter(line -> !line.isEmpty())
                    .map(String::trim)
                    .toList();
        } catch (Exception ex) {
            throw new BusinessException(String.format("exception occurred during reading from file: %s", file.getName()));
        }
    }
}
