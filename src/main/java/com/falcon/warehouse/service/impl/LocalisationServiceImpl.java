package com.falcon.warehouse.service.impl;

import com.falcon.warehouse.dto.LocalisationDto;
import com.falcon.warehouse.repository.LocalisationRepository;
import com.falcon.warehouse.service.LocalisationService;
import com.falcon.warehouse.service.mapper.LocalisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LocalisationServiceImpl implements LocalisationService {

    private final Logger log = LoggerFactory.getLogger(LocalisationServiceImpl.class);

    private final LocalisationRepository localisationRepository;
    private final LocalisationMapper localisationMapper;

    public LocalisationServiceImpl(LocalisationRepository localisationRepository, LocalisationMapper localisationMapper) {
        this.localisationRepository = localisationRepository;
        this.localisationMapper = localisationMapper;
    }

    @Override
    public LocalisationDto save(LocalisationDto localisationDto) {
        log.info("Saving localisation: {}", localisationDto.toString());
        return localisationMapper.convertToDto(localisationRepository.save(localisationMapper.convertToEntity(localisationDto)));
    }

    @Override
    public void delete(LocalisationDto localisationDto) {
        log.info("Deleting localisation {}", localisationDto);
        localisationRepository.delete(localisationMapper.convertToEntity(localisationDto));
    }

    @Override
    public List<LocalisationDto> getAll() {
        log.info("Getting all localisations");
        List<LocalisationDto> localisationsDto = new ArrayList<>();
        localisationRepository.findAll().forEach(localisation -> {
            localisationsDto.add(localisationMapper.convertToDto(localisation));
        });
        return localisationsDto;
    }

    @Override
    public LocalisationDto getByLocalisationIndex(String localisationIndex) {
        log.info("Searching localisation with index {}", localisationIndex);
        return localisationMapper.convertToDto(localisationRepository.findByLocalisationIndex(localisationIndex)
                .orElseThrow(EntityNotFoundException::new));
    }
}
