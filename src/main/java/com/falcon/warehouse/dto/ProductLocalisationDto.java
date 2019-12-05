package com.falcon.warehouse.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductLocalisationDto {

    private Long id;

    private BigDecimal quantityInLocalisation;

    private Long localisationId;

    private String localisationIndex;

    private Long productId;

    private String productIndex;
}
