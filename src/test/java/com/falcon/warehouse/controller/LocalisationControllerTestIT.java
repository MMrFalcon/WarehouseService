package com.falcon.warehouse.controller;

import com.falcon.warehouse.WarehouseApplication;
import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.LocalisationDto;
import com.falcon.warehouse.repository.AbstractRepository;
import com.falcon.warehouse.service.LocalisationService;
import com.falcon.warehouse.service.mapper.LocalisationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = WarehouseApplication.class)
@ActiveProfiles("test")
@Transactional
class LocalisationControllerTestIT extends AbstractRepository {

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
    private EntityManager entityManager;

    @Autowired
    private LocalisationService localisationService;

    @Autowired
    private LocalisationMapper localisationMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        final LocalisationController localisationController = new LocalisationController(localisationService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(localisationController).build();

    }

    @Test
    void getLocalisationByIndex() throws Exception {

        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        productLocalisations.add(productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build()));

        localisation.setProductLocalisations(productLocalisations);
        product.setProductLocalisations(productLocalisations);

        product = productRepository.saveAndFlush(product);
        localisation = localisationRepository.saveAndFlush(localisation);

        mockMvc.perform(get("/api/localisation/index/{localisationIndex}", LOCALISATION_INDEX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.localisationIndex").value(LOCALISATION_INDEX))
                .andExpect(jsonPath("$.localisationName").value(LOCALISATION_NAME))
                .andExpect(jsonPath("$.productLocalisations.[0].quantityInLocalisation").value(PRODUCT_LOCALISATION_QUANTITY));

    }

    @Test
    void getAllLocalisations() throws Exception {
        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        product = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());

        productLocalisations.add(productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(localisation)
                .product(product).quantityInLocalisation(PRODUCT_LOCALISATION_QUANTITY).build()));

        localisation.setProductLocalisations(productLocalisations);
        product.setProductLocalisations(productLocalisations);

        product = productRepository.saveAndFlush(product);
        localisation = localisationRepository.saveAndFlush(localisation);

        mockMvc.perform(get("/api/localisation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].localisationIndex").value(LOCALISATION_INDEX))
                .andExpect(jsonPath("$.[0].localisationName").value(LOCALISATION_NAME))
                .andExpect(jsonPath("$.[0].productLocalisations.[0].quantityInLocalisation").value(PRODUCT_LOCALISATION_QUANTITY));
    }

    @Test
    void createLocalisation() throws Exception {

        final String NEW_LOCALISATION_INDEX = "NEW_LOCALISATION_INDEX";
        final String NEW_LOCALISATION_NAME = "NEW_LOCALISATION_NAME";
        LocalisationDto localisationDto = new LocalisationDto();
        localisationDto.setLocalisationName(NEW_LOCALISATION_NAME);
        localisationDto.setLocalisationIndex(NEW_LOCALISATION_INDEX);

        mockMvc.perform(post("/api/localisation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(localisationDto)))
                .andExpect(status().isCreated());


    }

    @Test
    void updateLocalisation() throws Exception {
        final String UPDATED_LOCALISATION_INDEX = "UPDATED_LOCALISATION_INDEX";

        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        LocalisationDto localisationDto = localisationMapper.convertToDto(localisation);
        localisationDto.setLocalisationIndex(UPDATED_LOCALISATION_INDEX);

        mockMvc.perform(put("/api/localisation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(localisationDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteLocalisation() throws Exception {
        localisation = localisationRepository.saveAndFlush(Localisation.builder().localisationIndex(LOCALISATION_INDEX)
                .localisationName(LOCALISATION_NAME).build());

        LocalisationDto localisationDto = localisationMapper.convertToDto(localisation);

        mockMvc.perform(delete("/api/localisation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(localisationDto)))
                .andExpect(status().isNoContent());

    }
}