package com.falcon.warehouse.service.impl;

import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.ProductLocalisationDto;
import com.falcon.warehouse.repository.ProductLocalisationRepository;
import com.falcon.warehouse.service.ProductLocalisationService;
import com.falcon.warehouse.service.mapper.ProductLocalisationMapper;
import exceptions.EntityNotFoundException;
import exceptions.PairNotUnique;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class ProductLocalisationServiceImpl implements ProductLocalisationService {

    private final Logger log = LoggerFactory.getLogger(ProductLocalisationServiceImpl.class);

    private final ProductLocalisationRepository productLocalisationRepository;
    private final ProductLocalisationMapper productLocalisationMapper;

    public ProductLocalisationServiceImpl(ProductLocalisationRepository productLocalisationRepository, ProductLocalisationMapper productLocalisationMapper) {
        this.productLocalisationRepository = productLocalisationRepository;
        this.productLocalisationMapper = productLocalisationMapper;
    }

    @Override
    public ProductLocalisationDto save(ProductLocalisationDto productLocalisationDto) {
        log.info("Saving productLocalisation {}", productLocalisationDto);

        if (!isUniquePair(productLocalisationDto.getProductIndex(), productLocalisationDto.getLocalisationIndex()))
            throw new PairNotUnique("Pair localisation and product already exists in system");

        return productLocalisationMapper.convertToDto(productLocalisationRepository.save(
                productLocalisationMapper.convertToEntity(productLocalisationDto)
        ));
    }

    @Override
    public void delete(ProductLocalisationDto productLocalisationDto) {
        log.info("Deleting productLocalisation {}", productLocalisationDto);
        productLocalisationRepository.delete(productLocalisationMapper.convertToEntity(productLocalisationDto));
    }

    @Override
    public List<ProductLocalisationDto> getAll() {
        log.info("Getting product localisations");
        List<ProductLocalisationDto> productLocalisationDtoList = new ArrayList<>();
        productLocalisationRepository.findAll().forEach(productLocalisation -> {
            productLocalisationDtoList.add(productLocalisationMapper.convertToDto(productLocalisation));
        });
        return productLocalisationDtoList;
    }

    @Override
    public List<ProductLocalisationDto> getAllByLocalisationIndex(String localisationIndex) {
        log.info("Getting product localisations by localisation Index {}", localisationIndex);
        List<ProductLocalisationDto> productLocalisationDtoList = new ArrayList<>();
        Optional<List<ProductLocalisation>> optionalProductLocalisations = productLocalisationRepository
                .findAllByLocalisation_LocalisationIndexEquals(localisationIndex);

        if (optionalProductLocalisations.isPresent()) {
            optionalProductLocalisations.get().forEach(productLocalisation ->
                    productLocalisationDtoList.add(productLocalisationMapper.convertToDto(productLocalisation)));
            return productLocalisationDtoList;
        } else {
            throw new EntityNotFoundException("Product localisation(s) with localisation index " + localisationIndex + "does not exist");
        }
    }

    @Override
    public List<ProductLocalisationDto> getAllByProductIndex(String productIndex) {
        log.info("Getting product localisations by product Index {}", productIndex);
        List<ProductLocalisationDto> productLocalisationDtoList = new ArrayList<>();
        Optional<List<ProductLocalisation>> optionalProductLocalisations = productLocalisationRepository
                .findAllByProduct_ProductIndexEquals(productIndex);

        if (optionalProductLocalisations.isPresent()) {
            optionalProductLocalisations.get().forEach(productLocalisation ->
                    productLocalisationDtoList.add(productLocalisationMapper.convertToDto(productLocalisation)));
            return productLocalisationDtoList;
        } else {
            throw new EntityNotFoundException("Product localisation(s) with product index " + productIndex + "does not exist");
        }
    }

    @Override
    public boolean isUniquePair(String productIndex, String localisationIndex) {
        log.info("Checking if product {} and localisation {} pair is unique ", productIndex, localisationIndex);
        if (productLocalisationRepository.findAllByLocalisation_LocalisationIndexEquals(localisationIndex).isPresent()) {
            AtomicBoolean isUnique = new AtomicBoolean(true);
            List<ProductLocalisation> productLocalisations = productLocalisationRepository.
                    findAllByLocalisation_LocalisationIndexEquals(localisationIndex).get();

            productLocalisations.forEach(productLocalisation -> {
                if (productLocalisation.getProduct().getProductIndex().equals(productIndex)) {
                    isUnique.set(false);
                }
            });

            return isUnique.get();
        } else {
            return true;
        }
    }
}
