package com.falcon.warehouse.service.impl;

import com.falcon.warehouse.domain.Localisation;
import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.domain.ProductLocalisation;
import com.falcon.warehouse.dto.LocalisationDto;
import com.falcon.warehouse.dto.ProductDto;
import com.falcon.warehouse.repository.ProductRepository;
import com.falcon.warehouse.service.ProductService;
import com.falcon.warehouse.service.mapper.LocalisationMapper;
import com.falcon.warehouse.service.mapper.ProductMapper;
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
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final LocalisationMapper localisationMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, LocalisationMapper localisationMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.localisationMapper = localisationMapper;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        log.info("Saving {}", productDto.toString());
        return productMapper.convertToDto(productRepository.save(productMapper.convertToEntity(productDto)));
    }

    @Override
    public void delete(ProductDto productDto) {
        log.info("Deleting {}", productDto.toString());
        productRepository.delete(productMapper.convertToEntity(productDto));
    }

    @Override
    public List<ProductDto> getAll() {
        log.info("Getting all products");
        List<ProductDto> productDtoList = new ArrayList<>();
        productRepository.findAll().forEach(product -> productDtoList.add(productMapper.convertToDto(product)));
        return productDtoList;
    }

    @Override
    public ProductDto getByProductIndex(String productIndex) {
        log.info("Getting product by index {}", productIndex);
        return productMapper.convertToDto(productRepository.findByProductIndex(productIndex).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public ProductDto addLocalisation(String productIndex, LocalisationDto localisationDto, BigDecimal quantity) {
        log.info("Adding {} products {} to localisation {}", quantity, productIndex, localisationDto.getLocalisationIndex());

        Product product = productRepository.findByProductIndex(productIndex)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find product with index " + productIndex));

        Localisation localisation = localisationMapper.convertToEntity(localisationDto);

        ProductLocalisation productLocalisation = new ProductLocalisation();
        productLocalisation.setProduct(product);
        productLocalisation.setLocalisation(localisation);
        productLocalisation.setQuantityInLocalisation(quantity);

        product.addProductLocalisation(productLocalisation);

        return productMapper.convertToDto(productRepository.save(product));
    }
}
