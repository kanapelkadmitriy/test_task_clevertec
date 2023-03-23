package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.model.dto.ReceiptResponseDto;
import com.example.test_task_clevertec.service.ReceiptWriterService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.example.test_task_clevertec.util.Constants.RECEIPT_PATH;
import static com.example.test_task_clevertec.util.Constants.TEMPLATE_PDF_PATH;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class PdfReceiptWriterService implements ReceiptWriterService {

    private static final String PDF = ".pdf";
    private static final int LEADING = 15;
    private static final int SIZE = 15;
    private static final int QUANTITY_OF_EMPTY_LINES = 15;
    private static final int PAGE_NUMBER_OF_TEMPLATE = 1;
    private static final int X0 = 0;
    private static final int Y0 = 0;

    private final ReceiptDataGenerator receiptDataGenerator;

    @SneakyThrows
    @Override
    public void write(ReceiptResponseDto receiptResponse) {
        final List<String> receiptData = receiptDataGenerator.generateReceiptData(receiptResponse);
        String fileName = receiptData.get(1).substring(2);

        final Document document = new Document();
        final PdfWriter pdfWriter = PdfWriter.getInstance(document,
                new FileOutputStream(RECEIPT_PATH + fileName + PDF));
        document.open();
        importTemplate(pdfWriter, TEMPLATE_PDF_PATH);
        writeReceiptData(receiptData, document);
        document.close();
    }

    private void importTemplate(PdfWriter pdfWriter, String templatePath) throws IOException {
        final PdfReader reader = new PdfReader(templatePath);
        final PdfImportedPage importedPage = pdfWriter.getImportedPage(reader, PAGE_NUMBER_OF_TEMPLATE);
        pdfWriter.getDirectContent().addTemplate(importedPage, X0, Y0);
    }


    private void writeReceiptData(List<String> receiptData, Document document) throws DocumentException {
        final Font font = new Font(Font.FontFamily.TIMES_ROMAN, SIZE, Font.NORMAL);
        final Paragraph paragraph = new Paragraph();
        paragraph.add(new Paragraph("\n".repeat(QUANTITY_OF_EMPTY_LINES)));
        receiptData.forEach(line -> paragraph.add(new Paragraph(LEADING, line, font)));
        document.add(paragraph);
    }


}
