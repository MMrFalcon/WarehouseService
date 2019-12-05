package com.falcon.warehouse.repository;

import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.domain.ProductLocalisation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ProductLocalisationRepositoryTestIT extends AbstractRepository {

    private final String LOCALISATION_INDEX = "EXAMPLE INDEX";
    private final String LOCALISATION_NAME = "Rack 1";
    private final String PRODUCT_INDEX = "Product Index";
    private final String PRODUCT_NAME = "Product Name";
    private final BigDecimal PRODUCT_QUANTITY = new BigDecimal("123.23");
    private final BigDecimal QUANTITY_IN_LOCALISATION = new BigDecimal("23.23");

    private Localisation savedLocalisation;
    private Product savedProduct;

    @BeforeEach
    void setUp() {
        savedLocalisation = localisationRepository.saveAndFlush(Localisation.builder()
                .localisationIndex(LOCALISATION_INDEX).localisationName(LOCALISATION_NAME).build());
        savedProduct = productRepository.saveAndFlush(Product.builder().productIndex(PRODUCT_INDEX).name(PRODUCT_NAME).quantity(PRODUCT_QUANTITY).build());
        productLocalisationRepository.saveAndFlush(ProductLocalisation.builder().localisation(savedLocalisation).product(savedProduct).quantityInLocalisation(QUANTITY_IN_LOCALISATION).build());
    }

    @Test
    void findAllByLocalisation_LocalisationIndexEquals() {
        //Add the same Localisation but new Product
        Product newProductInLocalisation = productRepository.saveAndFlush(Product.builder()
                .productIndex("RANDOM").name("RANDOM_NAME").quantity(new BigDecimal("222.32")).build());

        productLocalisationRepository.saveAndFlush(ProductLocalisation.builder()
                .localisation(savedLocalisation).product(newProductInLocalisation).quantityInLocalisation(new BigDecimal("22.32")).build());

        Optional<List<ProductLocalisation>> foundProductLocalisation = productLocalisationRepository.findAllByLocalisation_LocalisationIndexEquals(LOCALISATION_INDEX);

        assertTrue(foundProductLocalisation.isPresent());
        //There are two products in one localisation
        assertEquals(2, foundProductLocalisation.get().size());
    }

    @Test
    void findAllByProduct_ProductIndexEquals() {
        //Add the same Product but new Localisation
        Localisation newLocalisationOfProduct = localisationRepository.saveAndFlush(Localisation.builder()
                .localisationIndex("RANDOM_LOC_INDEX").localisationName("RANDOM_LOC_NAME").build());

        productLocalisationRepository.saveAndFlush(ProductLocalisation.builder()
                .product(savedProduct).localisation(newLocalisationOfProduct).quantityInLocalisation(new BigDecimal("11.23")).build());

        Optional<List<ProductLocalisation>> foundProductLocalisations = productLocalisationRepository.findAllByProduct_ProductIndexEquals(PRODUCT_INDEX);

        assertTrue(foundProductLocalisations.isPresent());
        //There are two localisation of one product
        assertEquals(2, foundProductLocalisations.get().size());
    }
}