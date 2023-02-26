package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.model.dto.ProductReceiptDto;
import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.service.ReceiptWriterService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptWriterServiceImpl implements ReceiptWriterService {

    private static final String DOLLAR_SYMBOL = "$";
    private static final String RECEIPT_PATH = "receipt/";
    private static final String SEPARATE_SYMBOL = "=";
    private static final String SPACE_SYMBOL = " ";
    private static final String QTY_TITLE = "QTY";
    private static final String DESCRIPTION_TITLE = "DESCRIPTION";
    private static final String PRICE_TITLE = "PRICE";
    private static final String TOTAL_TITLE = "TOTAL";
    private static final String SPACE = "    ";

    @Override
    public void write(ReceiptResponseDto receiptResponse) {
        final List<String> receiptData = generateReceiptData(receiptResponse);
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

    private List<String> generateReceiptData(ReceiptResponseDto receiptResponse) {
        final List<String> receiptData = new ArrayList<>();
        String title = QTY_TITLE +
                getLineBySymbol(SPACE_SYMBOL, SPACE.length()) +
                DESCRIPTION_TITLE +
                getLineBySymbol(SPACE_SYMBOL, SPACE.length()) +
                PRICE_TITLE +
                getLineBySymbol(SPACE_SYMBOL, SPACE.length()) +
                getLineBySymbol(SPACE_SYMBOL, SPACE.length()) +
                TOTAL_TITLE;
        int totalReceiptWidth = title.length();

        receiptData.add("CASH RECEIPT");
        receiptData.add("№ " + receiptResponse.getReceiptNumber());
        receiptData.add("Date: " + receiptResponse.getDate());
        receiptData.add("Time: " + receiptResponse.getTime());
        receiptData.add(getLineBySymbol(SEPARATE_SYMBOL, totalReceiptWidth));
        receiptData.add(title);
        for (ProductReceiptDto productReceiptDto : receiptResponse.getProducts()) {
            String itemSb = productReceiptDto.getQuantity() +
                    getLineBySymbol(SPACE_SYMBOL,
                            (QTY_TITLE + SPACE).length() - String.valueOf(productReceiptDto.getQuantity()).length()) +
                    productReceiptDto.getDescription() +
                    getLineBySymbol(SPACE_SYMBOL,
                            (DESCRIPTION_TITLE + SPACE).length() - productReceiptDto.getDescription().length()) +
                    DOLLAR_SYMBOL +
                    productReceiptDto.getPrice() +
                    getLineBySymbol(SPACE_SYMBOL,
                            (PRICE_TITLE + SPACE).length() - String.valueOf(productReceiptDto.getPrice()).length()) +
                    DOLLAR_SYMBOL +
                    productReceiptDto.getTotal();

            receiptData.add(itemSb);
        }
        receiptData.add(getLineBySymbol(SEPARATE_SYMBOL, totalReceiptWidth));
        receiptData.add("TAXABLE TOT."
                + getLineBySymbol(SPACE_SYMBOL,
                totalReceiptWidth - "TAXABLE TOT.".length()
                        - (DOLLAR_SYMBOL + receiptResponse.getTotalSum()).length())
                + DOLLAR_SYMBOL + receiptResponse.getTotalSum());
        receiptData.add("VAT" + receiptResponse.getTotalDiscount() + "%"
                + getLineBySymbol(SPACE_SYMBOL,
                totalReceiptWidth - ("VAT" + receiptResponse.getTotalDiscount() + "%").length()
                        - (DOLLAR_SYMBOL + receiptResponse.getDiscountSum()).length())
                + DOLLAR_SYMBOL + receiptResponse.getDiscountSum());
        receiptData.add("TOTAL"
                + getLineBySymbol(SPACE_SYMBOL,
                totalReceiptWidth - "TOTAL".length() - (DOLLAR_SYMBOL + receiptResponse.getTotal()).length())
                + DOLLAR_SYMBOL + receiptResponse.getTotal());
        return receiptData;
    }

    private String getLineBySymbol(String symbol, int symbolWidth) {
        return Strings.repeat(symbol, symbolWidth);
    }
}
