package com.falcon.warehouse.dto;

import com.falcon.warehouse.domain.ProductLocalisation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class ProductDto {

    private Long id;

    private String productIndex;

    private String name;

    private BigDecimal quantity;

    private Set<ProductLocalisation> productLocalisations = new HashSet<>();
}
