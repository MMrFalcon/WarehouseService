package com.falcon.warehouse.repository;

import com.falcon.warehouse.domain.Localisation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class LocalisationRepositoryTestIT {

    private final String LOCALISATION_INDEX = "EXAMPLE INDEX";
    private final String LOCALISATION_NAME = "Rack 1";

    @Autowired
    private LocalisationRepository localisationRepository;

    @BeforeEach
    void setUp() {
        localisationRepository.saveAndFlush(Localisation.builder()
                .localisationIndex(LOCALISATION_INDEX).localisationName(LOCALISATION_NAME).build());
    }

    @Test
    void findByLocalisationIndex() {
        int databaseSize = localisationRepository.findAll().size();

        Optional<Localisation> foundLocalisation = localisationRepository.findByLocalisationIndex(LOCALISATION_INDEX);

        assertTrue(foundLocalisation.isPresent());
        assertEquals(LOCALISATION_INDEX, foundLocalisation.get().getLocalisationIndex());
        assertEquals(LOCALISATION_NAME, foundLocalisation.get().getLocalisationName());
        assertEquals(databaseSize, localisationRepository.findAll().size());
    }
}