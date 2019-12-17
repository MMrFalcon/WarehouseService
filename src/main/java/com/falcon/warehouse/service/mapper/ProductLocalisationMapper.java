package com.falcon.warehouse.service.mapper;

import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.ProductLocalisationDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductLocalisationMapper {

    private final ModelMapper modelMapper;

    public ProductLocalisationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductLocalisationDto convertToDto(ProductLocalisation productLocalisation) {
        return modelMapper.map(productLocalisation, ProductLocalisationDto.class);
    }

    public ProductLocalisation convertToEntity(ProductLocalisationDto productLocalisationDto) {
        return modelMapper.map(productLocalisationDto, ProductLocalisation.class);
    }
}
