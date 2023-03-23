package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.model.dto.ProductReceiptDto;
import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StandardReceiptDataGenerator implements ReceiptDataGenerator {

    private static final String DOLLAR_SYMBOL = "$";
    private static final String SEPARATE_SYMBOL = "=";
    private static final String SPACE_SYMBOL = " ";
//    private static final String QTY_TITLE = "QTY";
//    private static final String DESCRIPTION_TITLE = "DESCRIPTION";
//    private static final String PRICE_TITLE = "PRICE";
//    private static final String TOTAL_TITLE = "TOTAL";
    private static final String QTY_TITLE = "qty";
    private static final String DESCRIPTION_TITLE = "description";
    private static final String PRICE_TITLE = "price";
    private static final String TOTAL_TITLE = "total";
    private static final String SPACE = "    ";

    @Override
    public List<String> generateReceiptData(ReceiptResponseDto receiptResponse) {
        final List<String> receiptData = new ArrayList<>();
        final int qtyLength = getLengthOfQty(receiptResponse);
        final int descriptionLength = getLengthOfDescription(receiptResponse);
        final int priceLength = getLengthOfPrice(receiptResponse);
        final int totalLength = getLengthOfTotal(receiptResponse);

        int totalReceiptWidth = qtyLength + SPACE.length() +
                descriptionLength + SPACE.length() +
                priceLength + SPACE.length() +
                totalLength;
        receiptData.add("CASH RECEIPT");
        receiptData.add("N " + receiptResponse.getReceiptNumber());
        receiptData.add("Date: " + receiptResponse.getDate());
        receiptData.add("Time: " + receiptResponse.getTime());
        receiptData.add(getLineBySymbol(SEPARATE_SYMBOL, totalReceiptWidth));

        String title = lineWithSpaceGenerate(QTY_TITLE, qtyLength) +
                lineWithSpaceGenerate(DESCRIPTION_TITLE, descriptionLength) +
                lineWithSpaceGenerate(PRICE_TITLE, priceLength) +
                TOTAL_TITLE;
        receiptData.add(title);

        for (ProductReceiptDto productReceiptDto : receiptResponse.getProducts()) {
            String itemSb = lineWithSpaceGenerate(String.valueOf(productReceiptDto.getQuantity()), qtyLength) +
                    lineWithSpaceGenerate(productReceiptDto.getDescription(), descriptionLength) +
                    lineWithSpaceGenerate(productReceiptDto.getPrice() + DOLLAR_SYMBOL, descriptionLength) +
                    productReceiptDto.getTotal() + DOLLAR_SYMBOL;
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

    private int getLengthOfQty(ReceiptResponseDto receiptResponseDto) {
        final int lengthOfQty = receiptResponseDto.getProducts().stream()
                .mapToInt(productReceiptDto -> String.valueOf(productReceiptDto.getQuantity()).length())
                .max()
                .orElse(QTY_TITLE.length());
        return Math.max(lengthOfQty, QTY_TITLE.length());
    }

    private int getLengthOfDescription(ReceiptResponseDto receiptResponseDto) {
        final int lengthOfQty = receiptResponseDto.getProducts().stream()
                .mapToInt(productReceiptDto -> String.valueOf(productReceiptDto.getDescription()).length())
                .max()
                .orElse(DESCRIPTION_TITLE.length());
        return Math.max(lengthOfQty, DESCRIPTION_TITLE.length());
    }

    private int getLengthOfPrice(ReceiptResponseDto receiptResponseDto) {
        final int lengthOfQty = receiptResponseDto.getProducts().stream()
                .mapToInt(productReceiptDto -> (productReceiptDto.getPrice() + DOLLAR_SYMBOL).length())
                .max()
                .orElse(PRICE_TITLE.length());
        return Math.max(lengthOfQty, PRICE_TITLE.length());
    }

    private int getLengthOfTotal(ReceiptResponseDto receiptResponseDto) {
        final int lengthOfQty = receiptResponseDto.getProducts().stream()
                .mapToInt(productReceiptDto -> (productReceiptDto.getTotal() + DOLLAR_SYMBOL).length())
                .max()
                .orElse(TOTAL_TITLE.length());
        return Math.max(lengthOfQty, TOTAL_TITLE.length());
    }

    private String lineWithSpaceGenerate(String line, int maxLength) {
        int quantityOfAdditionSpaces = maxLength - line.length();
        return quantityOfAdditionSpaces > 0 ?
                line + getLineBySymbol(SPACE_SYMBOL, quantityOfAdditionSpaces) + SPACE
                : line + SPACE;
    }


    private String getLineBySymbol(String symbol, int symbolWidth) {
        return Strings.repeat(symbol, symbolWidth);
    }
}