package com.falcon.warehouse.service.mapper;

import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.LocalisationDto;
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
class LocalisationMapperTest {
    private final Long LOCALISATION_ID = 1L;
    private final String LOCALISATION_INDEX = "INDEX EXAMPLE";
    private final String LOCALISATION_NAME = "Example Name";

    private Localisation localisation;
    private Set<ProductLocalisation> productLocalisationSet = new HashSet<>();

    @Autowired
    private LocalisationMapper localisationMapper;

    @BeforeEach
    void setUp() {
        productLocalisationSet.add(ProductLocalisation.builder().quantityInLocalisation(new BigDecimal("12.22")).build());
        localisation = Localisation.builder().id(LOCALISATION_ID).localisationName(LOCALISATION_NAME)
                .localisationIndex(LOCALISATION_INDEX).productLocalisations(productLocalisationSet).build();
    }

    @Test
    void convertToDto() {
        LocalisationDto localisationDto = localisationMapper.convertToDto(localisation);
        assertEquals(LOCALISATION_ID, localisationDto.getId());
        assertEquals(LOCALISATION_INDEX, localisationDto.getLocalisationIndex());
        assertEquals(LOCALISATION_NAME, localisationDto.getLocalisationName());
        assertEquals(1, localisationDto.getProductLocalisations().size());
    }

    @Test
    void convertToEntity() {
        final String NEW_LOCALISATION_INDEX = "NEW  LOCALISATION INDEX";
        LocalisationDto localisationDto = new LocalisationDto();
        localisationDto.setId(LOCALISATION_ID);
        localisationDto.setLocalisationIndex(NEW_LOCALISATION_INDEX);
        localisationDto.setLocalisationName(LOCALISATION_NAME);
        localisationDto.setProductLocalisations(productLocalisationSet);

        Localisation localisation = localisationMapper.convertToEntity(localisationDto);
        assertEquals(NEW_LOCALISATION_INDEX, localisation.getLocalisationIndex());
        assertEquals(LOCALISATION_ID, localisation.getId());
        assertEquals(LOCALISATION_NAME, localisation.getLocalisationName());
        assertEquals(1, localisation.getProductLocalisations().size());
    }

}