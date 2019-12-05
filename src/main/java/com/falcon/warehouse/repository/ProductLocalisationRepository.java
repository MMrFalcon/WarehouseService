package com.falcon.warehouse.repository;

import com.falcon.warehouse.domain.ProductLocalisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLocalisationRepository extends JpaRepository<ProductLocalisation, Long> {
}
