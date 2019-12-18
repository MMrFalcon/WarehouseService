package com.falcon.warehouse.service;

import com.falcon.warehouse.dto.LocalisationDto;

import java.util.List;

public interface LocalisationService {

    LocalisationDto save(LocalisationDto localisationDto);

    void delete(LocalisationDto localisationDto);

    List<LocalisationDto> getAll();

    LocalisationDto getByLocalisationIndex(String LocalisationIndex);
}
