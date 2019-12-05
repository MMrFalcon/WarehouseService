package com.falcon.warehouse.repository;

import com.falcon.warehouse.domain.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalisationRepository extends JpaRepository<Localisation, Long> {
    Optional<Localisation> findByLocalisationIndex(String localisationIndex);
}
