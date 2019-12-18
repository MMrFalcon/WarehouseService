package com.falcon.warehouse.service.impl;

import com.falcon.warehouse.dto.ProductDto;
import com.falcon.warehouse.repository.ProductRepository;
import com.falcon.warehouse.service.ProductService;
import com.falcon.warehouse.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
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
}
