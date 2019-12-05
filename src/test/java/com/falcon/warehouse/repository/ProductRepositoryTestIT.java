package com.falcon.warehouse.repository;

import com.falcon.warehouse.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTestIT extends AbstractRepository {

    private final String PRODUCT_INDEX = "Product Index";
    private final String PRODUCT_NAME= "Product Name";
    private final BigDecimal PRODUCT_QUANTITY = new BigDecimal("123.23");

    @BeforeEach
    void setUp() {
        productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());
    }

    @Test
    void findByProductIndex() {
        int databaseSie = productRepository.findAll().size();

        Optional<Product> foundProduct = productRepository.findByProductIndex(PRODUCT_INDEX);

        assertTrue(foundProduct.isPresent());
        assertEquals(PRODUCT_INDEX, foundProduct.get().getProductIndex());
        assertEquals(PRODUCT_NAME, foundProduct.get().getName());
        assertEquals(PRODUCT_QUANTITY, foundProduct.get().getQuantity());
        assertEquals(databaseSie, productRepository.findAll().size());
    }
}