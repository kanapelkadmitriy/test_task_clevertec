package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.service.ReceiptWriterService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;

import static com.example.test_task_clevertec.util.Constants.TEMPLATE_PDF_PATH;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class PdfReceiptWriterService implements ReceiptWriterService {

    private final ReceiptDataGenerator receiptDataGenerator;

    @SneakyThrows
    @Override
    public void write(ReceiptResponseDto receiptResponse) {
        final List<String> receiptData = receiptDataGenerator.generateReceiptData(receiptResponse);
        PdfReader reader = new PdfReader(TEMPLATE_PDF_PATH);
        System.out.println("----");
    }



}
