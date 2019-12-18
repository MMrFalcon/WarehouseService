package com.falcon.warehouse.service.mapper;

import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.ProductLocalisationDto;
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
class ProductLocalisationMapperTest {

    private final Long PRODUCT_LOCALISATION_ID = 12L;
    private final BigDecimal QUANTITY_IN_LOCALISATION = new BigDecimal("123.123");

    private final Long LOCALISATION_ID = 1L;
    private final String LOCALISATION_INDEX = "INDEX EXAMPLE";
    private final String LOCALISATION_NAME = "Example Name";

    private final Long PRODUCT_ID = 2L;
    private final String PRODUCT_NAME = "PRODUCT NAME";
    private final String PRODUCT_INDEX = "PRODUCT INDEX";
    private final BigDecimal PRODUCT_QUANTITY = new BigDecimal("12.213");

    private ProductLocalisation productLocalisation;
    private Product product;
    private Localisation localisation;

    @Autowired
    private ProductLocalisationMapper productLocalisationMapper;

    @BeforeEach
    void setUp() {
        localisation = Localisation.builder().id(LOCALISATION_ID).localisationName(LOCALISATION_NAME)
                .localisationIndex(LOCALISATION_INDEX).build();
        product = Product.builder().id(PRODUCT_ID).quantity(PRODUCT_QUANTITY).name(PRODUCT_NAME).productIndex(PRODUCT_INDEX).build();

        productLocalisation = ProductLocalisation.builder().id(PRODUCT_LOCALISATION_ID).quantityInLocalisation(QUANTITY_IN_LOCALISATION)
                .product(product).localisation(localisation).build();

        Set<ProductLocalisation> productLocalisationSet = new HashSet<>();
        productLocalisationSet.add(productLocalisation);

        product.setProductLocalisations(productLocalisationSet);
        localisation.setProductLocalisations(productLocalisationSet);
    }

    @Test
    void convertToDto() {
        ProductLocalisationDto productLocalisationDto = productLocalisationMapper.convertToDto(productLocalisation);
        assertEquals(QUANTITY_IN_LOCALISATION, productLocalisationDto.getQuantityInLocalisation());
        assertEquals(PRODUCT_LOCALISATION_ID, productLocalisationDto.getId());
        assertEquals(PRODUCT_ID, productLocalisationDto.getProductId());
        assertEquals(PRODUCT_INDEX, productLocalisationDto.getProductIndex());
        assertEquals(LOCALISATION_ID, productLocalisationDto.getLocalisationId());
        assertEquals(LOCALISATION_INDEX, productLocalisationDto.getLocalisationIndex());
    }

    @Test
    void convertToEntity() {
        final BigDecimal NEW_QUANTITY_IN_LOCALISATION = new BigDecimal("333.123");
        ProductLocalisationDto productLocalisationDto = new ProductLocalisationDto();
        productLocalisationDto.setQuantityInLocalisation(NEW_QUANTITY_IN_LOCALISATION);
        productLocalisationDto.setId(PRODUCT_LOCALISATION_ID);
        productLocalisationDto.setLocalisationId(LOCALISATION_ID);
        productLocalisationDto.setProductId(PRODUCT_ID);
        productLocalisationDto.setLocalisationIndex(LOCALISATION_INDEX);
        productLocalisationDto.setProductIndex(PRODUCT_INDEX);

        ProductLocalisation productLocalisation = productLocalisationMapper.convertToEntity(productLocalisationDto);
        assertEquals(NEW_QUANTITY_IN_LOCALISATION, productLocalisation.getQuantityInLocalisation());
        assertEquals(PRODUCT_LOCALISATION_ID, productLocalisation.getId());
        assertEquals(PRODUCT_ID, productLocalisation.getProduct().getId());
        assertEquals(PRODUCT_INDEX, productLocalisation.getProduct().getProductIndex());
        assertEquals(LOCALISATION_ID, productLocalisation.getLocalisation().getId());
        assertEquals(LOCALISATION_INDEX, productLocalisation.getLocalisation().getLocalisationIndex());
    }
}