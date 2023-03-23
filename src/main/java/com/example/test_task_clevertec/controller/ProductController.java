package com.example.test_task_clevertec.controller;

import com.example.test_task_clevertec.model.dto.ProductDto;
import com.example.test_task_clevertec.model.entity.Product;
import com.example.test_task_clevertec.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.test_task_clevertec.controller.ProductController.ROOT_URL;

@RestController
@RequestMapping(ROOT_URL)
public class ProductController {

    public final static String ROOT_URL = "/product";
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{product_id}")
    public ProductDto findById(@PathVariable(name = "product_id") Long productId){
        return productService.getById(productId);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @PutMapping("/{product_id}")
    public ProductDto updateProduct(@PathVariable(name = "product_id") Long productId,
                                    @RequestBody ProductDto productDto){
        return productService.updateProduct(productId,productDto);
    }

    @DeleteMapping("/{product_id}")
    public void deleteProduct(@PathVariable(name = "product_id") Long id) {
        productService.deleteProduct(id);
    }

}
