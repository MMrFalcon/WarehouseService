package com.falcon.warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LocalisationRepository localisationRepository;

    @Autowired
    ProductLocalisationRepository productLocalisationRepository;
}
