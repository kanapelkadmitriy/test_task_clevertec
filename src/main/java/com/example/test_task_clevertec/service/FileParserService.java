package com.example.test_task_clevertec.service;

import com.example.test_task_clevertec.model.dto.ReceiptRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileParserService {

    ReceiptRequestDto parse(MultipartFile file);
}
