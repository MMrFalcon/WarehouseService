package com.falcon.warehouse.service.mapper;

import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.dto.LocalisationDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LocalisationMapper {

    private final ModelMapper modelMapper;

    public LocalisationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LocalisationDto convertToDto(Localisation localisation) {
        return modelMapper.map(localisation, LocalisationDto.class);
    }

    public Localisation convertToEntity(LocalisationDto localisationDto) {
        return modelMapper.map(localisationDto, Localisation.class);
    }
}
