package com.falcon.warehouse.service;

import com.falcon.warehouse.dto.ProductLocalisationDto;

import java.util.List;

public interface ProductLocalisationService  {

    ProductLocalisationDto save(ProductLocalisationDto productLocalisationDto);

    void delete(ProductLocalisationDto productLocalisationDto);

    List<ProductLocalisationDto> getAll();

    List<ProductLocalisationDto> getAllByLocalisationIndex(String localisationIndex);

    List<ProductLocalisationDto> getAllByProductIndex(String productIndex);
}
