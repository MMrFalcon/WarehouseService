package com.falcon.warehouse.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class LocalisationDto {

    private Long id;

    private String localisationIndex;

    private String localisationName;

    private Set<ProductLocalisationDto> productLocalisations = new HashSet<>();
}
