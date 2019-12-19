package com.falcon.warehouse.controller;

import com.falcon.warehouse.WarehouseApplication;
import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.ProductDto;
import com.falcon.warehouse.repository.AbstractRepository;
import com.falcon.warehouse.service.ProductService;
import com.falcon.warehouse.service.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = WarehouseApplication.class)
@ActiveProfiles("test")
@Transactional
class ProductControllerTest extends AbstractRepository {

    private final String LOCALISATION_INDEX = "RandomIndex123";
    private final String LOCALISATION_NAME = "RANDOM NAME";

    private final String PRODUCT_INDEX = "RandomProdIndex123";
    private final String PRODUCT_NAME = "RANDOM_PROD_NAME";
    private final BigDecimal PRODUCT_QUANTITY = new BigDecimal("123.123");

    private final BigDecimal PRODUCT_LOCALISATION_QUANTITY = new BigDecimal("100.123");

    private Set<ProductLocalisation> productLocalisations = new HashSet<>();

    private Localisation localisation;
    private Product product;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        final ProductController productController = new ProductController(productService);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getProductByIndex() throws Exception {
        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        productLocalisations.add(productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build()));

        localisation.setProductLocalisations(productLocalisations);
        product.setProductLocalisations(productLocalisations);

        product = productRepository.saveAndFlush(product);
        localisation = localisationRepository.saveAndFlush(localisation);

        mockMvc.perform(get("/api/product/index/{productIndex}", PRODUCT_INDEX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productIndex").value(PRODUCT_INDEX))
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.quantity").value(PRODUCT_QUANTITY))
                .andExpect(jsonPath("$.productLocalisations.[0].quantityInLocalisation").value(PRODUCT_LOCALISATION_QUANTITY));
    }

    @Test
    void getAllProducts() throws Exception {

        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        productLocalisations.add(productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build()));

        localisation.setProductLocalisations(productLocalisations);
        product.setProductLocalisations(productLocalisations);

        product = productRepository.saveAndFlush(product);
        localisation = localisationRepository.saveAndFlush(localisation);

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].productIndex").value(PRODUCT_INDEX))
                .andExpect(jsonPath("$.[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.[0].productLocalisations.[0].quantityInLocalisation").value(PRODUCT_LOCALISATION_QUANTITY));
    }

    @Test
    void createProduct() throws Exception {

        ProductDto productDto = new ProductDto();
        productDto.setQuantity(new BigDecimal("1234.123"));
        productDto.setProductIndex("RANDOMINDEX_VOL1");
        productDto.setName("RANDOM PRODUCT NAME 2");

        mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(productDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateProduct() throws Exception {
        final String NEW_PRODUCT_INDEX = "RANDOM_INDEX+_123";
        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());
        ProductDto productDto = productMapper.convertToDto(product);
        product.setProductIndex(NEW_PRODUCT_INDEX);

        mockMvc.perform(put("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(productDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());
        ProductDto productDto = productMapper.convertToDto(product);

        mockMvc.perform(delete("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(productDto)))
                .andExpect(status().isNoContent());
    }
}