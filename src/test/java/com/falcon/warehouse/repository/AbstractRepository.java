package com.falcon.warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository {

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public LocalisationRepository localisationRepository;

    @Autowired
    public ProductLocalisationRepository productLocalisationRepository;
}
