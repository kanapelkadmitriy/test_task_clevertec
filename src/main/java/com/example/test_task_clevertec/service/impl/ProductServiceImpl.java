package com.example.test_task_clevertec.service.impl;

import com.example.test_task_clevertec.exceptions.BusinessException;
import com.example.test_task_clevertec.model.dto.ProductDto;
import com.example.test_task_clevertec.model.entity.Product;
import com.example.test_task_clevertec.repository.ProductRepository;
import com.example.test_task_clevertec.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public static final Pattern VENDOR_CODE_PATTERN = Pattern.compile("[A-Z]{2}+-\\d{5}");

    private final ProductRepository productRepository;

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format("product with id %s not fouond", id)));
    }

    @Override
    public ProductDto getById(Long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format("product with id %s not fouond", id)));
        return ProductDto.builder()
                .vendorCode(product.getVendorCode())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .description(product.getDescription())
                .isDiscounted(product.isDiscounted())
                .build();
    }

    @Transactional
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        validateVendorCode(productDto.getVendorCode());
        Product product = Product.builder()
                .vendorCode(productDto.getVendorCode())
                .description(productDto.getDescription())
                .isDiscounted(productDto.isDiscounted())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();
        productRepository.save(product);
        return productDto;
    }
    @Transactional
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        validateVendorCode(productDto.getVendorCode());
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format("product with id %s not found", id)));
        product.setVendorCode(product.getVendorCode());
        product.setDescription(productDto.getDescription());
        product.setDiscounted(productDto.isDiscounted());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepository.save(product);
        return productDto;
    }
    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private void validateVendorCode(String vendorCode) {
        if (vendorCode != null && !VENDOR_CODE_PATTERN.matcher(vendorCode).matches()) {
            throw new BusinessException(String.format("invalid vendor code: %s", vendorCode));
        }
    }
}
