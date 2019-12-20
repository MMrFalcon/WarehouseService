package com.falcon.warehouse.controller;

import com.falcon.warehouse.WarehouseApplication;
import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.ProductLocalisationDto;
import com.falcon.warehouse.repository.AbstractRepository;
import com.falcon.warehouse.service.ProductLocalisationService;
import com.falcon.warehouse.service.mapper.ProductLocalisationMapper;
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
class ProductLocalisationControllerTest extends AbstractRepository {


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
    private ProductLocalisationService productLocalisationService;

    @Autowired
    private ProductLocalisationMapper productLocalisationMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        final ProductLocalisationController productLocalisationController = new ProductLocalisationController(productLocalisationService);

        mockMvc = MockMvcBuilders.standaloneSetup(productLocalisationController).build();
    }

    @Test
    void getAllProdLocalisationsByProdIndex() throws Exception {
        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        productLocalisations.add(productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build()));

        localisation.setProductLocalisations(productLocalisations);
        product.setProductLocalisations(productLocalisations);

        product = productRepository.saveAndFlush(product);
        localisation = localisationRepository.saveAndFlush(localisation);

        mockMvc.perform(get("/api/productLocalisation/localisationIndex/{localisationIndex}", LOCALISATION_INDEX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].quantityInLocalisation").value(PRODUCT_LOCALISATION_QUANTITY))
                .andExpect(jsonPath("$.[0].localisationId").value(localisation.getId()))
                .andExpect(jsonPath("$.[0].localisationIndex").value(LOCALISATION_INDEX))
                .andExpect(jsonPath("$.[0].productId").value(product.getId()))
                .andExpect(jsonPath("$.[0].productIndex").value(PRODUCT_INDEX));


    }

    @Test
    void getAllProdLocalisationsByLocIndex() throws Exception {

        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        productLocalisations.add(productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build()));

        localisation.setProductLocalisations(productLocalisations);
        product.setProductLocalisations(productLocalisations);

        product = productRepository.saveAndFlush(product);
        localisation = localisationRepository.saveAndFlush(localisation);

        mockMvc.perform(get("/api/productLocalisation/productIndex/{productIndex}", PRODUCT_INDEX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].quantityInLocalisation").value(PRODUCT_LOCALISATION_QUANTITY))
                .andExpect(jsonPath("$.[0].localisationId").value(localisation.getId()))
                .andExpect(jsonPath("$.[0].localisationIndex").value(LOCALISATION_INDEX))
                .andExpect(jsonPath("$.[0].productId").value(product.getId()))
                .andExpect(jsonPath("$.[0].productIndex").value(PRODUCT_INDEX));
    }

    @Test
    void getAllProductLocalisations() throws Exception {

        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        productLocalisations.add(productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build()));

        localisation.setProductLocalisations(productLocalisations);
        product.setProductLocalisations(productLocalisations);

        product = productRepository.saveAndFlush(product);
        localisation = localisationRepository.saveAndFlush(localisation);

        mockMvc.perform(get("/api/productLocalisation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].quantityInLocalisation").value(PRODUCT_LOCALISATION_QUANTITY))
                .andExpect(jsonPath("$.[0].localisationId").value(localisation.getId()))
                .andExpect(jsonPath("$.[0].localisationIndex").value(LOCALISATION_INDEX))
                .andExpect(jsonPath("$.[0].productId").value(product.getId()))
                .andExpect(jsonPath("$.[0].productIndex").value(PRODUCT_INDEX));
    }

    @Test
    void createProductLocalisation() throws Exception {
        ProductLocalisationDto productLocalisationDto = new ProductLocalisationDto();
        productLocalisationDto.setQuantityInLocalisation(new BigDecimal("332112.123"));
        productLocalisationDto.setProductIndex("RANDOM_PRODUCT_INDEX_123");
        productLocalisationDto.setLocalisationIndex("RANDOM_LOCALISATION_INDEX_3321");

        mockMvc.perform(post("/api/productLocalisation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(productLocalisationDto)))
                .andExpect(status().isCreated());


    }

    @Test
    void updateProductLocalisation() throws Exception {
        final BigDecimal NEW_PRODUCT_QUANTITY_IN_LOC = new BigDecimal("123333342.123");

        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        ProductLocalisation productLocalisation = productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build());

        ProductLocalisationDto productLocalisationDto = productLocalisationMapper.convertToDto(productLocalisation);
        productLocalisationDto.setQuantityInLocalisation(NEW_PRODUCT_QUANTITY_IN_LOC);

        mockMvc.perform(put("/api/productLocalisation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(productLocalisationDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductLocalisation() throws Exception {

        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        ProductLocalisation productLocalisation = productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build());

        ProductLocalisationDto productLocalisationDto = productLocalisationMapper.convertToDto(productLocalisation);

        mockMvc.perform(delete("/api/productLocalisation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(productLocalisationDto)))
                .andExpect(status().isNoContent());

    }
}