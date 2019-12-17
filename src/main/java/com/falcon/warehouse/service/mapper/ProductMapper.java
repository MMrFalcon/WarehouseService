package com.falcon.warehouse.service.mapper;

import com.falcon.warehouse.domain.Product;
import com.falcon.warehouse.dto.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDto convertToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public Product convertToEntity(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }
}
