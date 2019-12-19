package com.falcon.warehouse.controller;

import com.falcon.warehouse.dto.ProductDto;
import com.falcon.warehouse.service.ProductService;
import exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/product/index/{productIndex}")
    public ResponseEntity<ProductDto> getProductByIndex(@PathVariable String productIndex) {
        log.info("Rest request to get product by index {}", productIndex);
        ProductDto productDto = productService.getByProductIndex(productIndex);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info("Rest request to get all products");
        List<ProductDto> ProductDtoList = productService.getAll();
        return new ResponseEntity<>(ProductDtoList, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Rest request to save product {}", productDto);
        if (productDto.getId() != null) {
            throw new BadRequestException("New product cannot already have an ID");
        }

        ProductDto savedProduct = productService.save(productDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/product")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Rest request to update product {}", productDto);
        if (productDto.getId() == null) {
            throw new BadRequestException("Invalid id");
        }

        ProductDto updatedProduct = productService.save(productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/product")
    public ResponseEntity<Void> deleteProduct(@RequestBody ProductDto productDto) {
        log.info("Rest request to delete product {}", productDto);
        productService.delete(productDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
