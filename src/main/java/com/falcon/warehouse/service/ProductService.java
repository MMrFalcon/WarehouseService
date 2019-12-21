package com.falcon.warehouse.service;

import com.falcon.warehouse.dto.LocalisationDto;
import com.falcon.warehouse.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto productDto);

    void delete(ProductDto productDto);

    List<ProductDto> getAll();

    ProductDto getByProductIndex(String productIndex);

    ProductDto addLocalisation(String productIndex, LocalisationDto localisationDto, BigDecimal quantity);

}
