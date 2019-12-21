package com.falcon.warehouse.service.impl;

import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.LocalisationDto;
import com.falcon.warehouse.dto.ProductDto;
import com.falcon.warehouse.repository.LocalisationRepository;
import com.falcon.warehouse.service.LocalisationService;
import com.falcon.warehouse.service.ProductLocalisationService;
import com.falcon.warehouse.service.mapper.LocalisationMapper;
import com.falcon.warehouse.service.mapper.ProductMapper;
import exceptions.PairNotUnique;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LocalisationServiceImpl implements LocalisationService {

    private final Logger log = LoggerFactory.getLogger(LocalisationServiceImpl.class);

    private final LocalisationRepository localisationRepository;
    private final LocalisationMapper localisationMapper;
    private final ProductMapper productMapper;
    private final ProductLocalisationService productLocalisationService;

    public LocalisationServiceImpl(LocalisationRepository localisationRepository, LocalisationMapper localisationMapper,
                                   ProductMapper productMapper, ProductLocalisationService productLocalisationService) {

        this.localisationRepository = localisationRepository;
        this.localisationMapper = localisationMapper;
        this.productMapper = productMapper;
        this.productLocalisationService = productLocalisationService;
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

    @Override
    public LocalisationDto addProduct(String localisationIndex, ProductDto productDto, BigDecimal quantity) {
        log.info("Adding {} products {} to localisation {}", quantity, productDto.getProductIndex(), localisationIndex);

        Localisation localisation = localisationRepository.findByLocalisationIndex(localisationIndex)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find localisation with index " + localisationIndex));

        if (!productLocalisationService.isUniquePair(productDto.getProductIndex(), localisationIndex))
            throw new PairNotUnique("Pair localisation and product already exists in system");

        Product product = productMapper.convertToEntity(productDto);

        ProductLocalisation productLocalisation = new ProductLocalisation();
        productLocalisation.setLocalisation(localisation);
        productLocalisation.setProduct(product);
        productLocalisation.setQuantityInLocalisation(quantity);

        localisation.addProductLocalisation(productLocalisation);
        return localisationMapper.convertToDto(localisationRepository.save(localisation));
    }
}
