package com.falcon.warehouse.service.mapper;

import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class ProductMapperTest {

    private final Long PRODUCT_ID = 2L;
    private final String PRODUCT_NAME = "PRODUCT NAME";
    private final String PRODUCT_INDEX = "PRODUCT INDEX";
    private final BigDecimal PRODUCT_QUANTITY = new BigDecimal("12.213");

    private Product product;
    private Set<ProductLocalisation> productLocalisationSet = new HashSet<>();

    @Autowired
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productLocalisationSet.add(ProductLocalisation.builder().quantityInLocalisation(new BigDecimal("12.22")).build());
        product = Product.builder().id(PRODUCT_ID).quantity(PRODUCT_QUANTITY).name(PRODUCT_NAME).productIndex(PRODUCT_INDEX)
                .productLocalisations(productLocalisationSet).build();
    }

    @Test
    void convertToDto() {
        ProductDto productDto = productMapper.convertToDto(product);
        assertEquals(PRODUCT_ID, productDto.getId());
        assertEquals(PRODUCT_INDEX, productDto.getProductIndex());
        assertEquals(PRODUCT_NAME, productDto.getName());
        assertEquals(PRODUCT_QUANTITY, productDto.getQuantity());
        assertEquals(1, productDto.getProductLocalisations().size());
    }

    @Test
    void convertToEntity() {
        final String NEW_PRODUCT_INDEX = "NEW PRODUCT INDEX";
        ProductDto productDto = new ProductDto();
        productDto.setId(PRODUCT_ID);
        productDto.setName(PRODUCT_NAME);
        productDto.setProductIndex(NEW_PRODUCT_INDEX);
        productDto.setQuantity(PRODUCT_QUANTITY);
        productDto.setProductLocalisations(productLocalisationSet);

        Product product = productMapper.convertToEntity(productDto);
        assertEquals(PRODUCT_ID, product.getId());
        assertEquals(PRODUCT_QUANTITY, product.getQuantity());
        assertEquals(NEW_PRODUCT_INDEX, product.getProductIndex());
        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(1, product.getProductLocalisations().size());
    }
}