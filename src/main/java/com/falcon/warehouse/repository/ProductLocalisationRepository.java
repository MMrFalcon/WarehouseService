package com.falcon.warehouse.repository;

import com.falcon.warehouse.domain.ProductLocalisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductLocalisationRepository extends JpaRepository<ProductLocalisation, Long> {

    Optional<List<ProductLocalisation>> findAllByLocalisation_LocalisationIndexEquals(String localisationIndex);
    Optional<List<ProductLocalisation>> findAllByProduct_ProductIndexEquals(String productIndex);
}
