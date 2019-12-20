package com.falcon.warehouse.controller;

import com.falcon.warehouse.dto.ProductLocalisationDto;
import com.falcon.warehouse.service.ProductLocalisationService;
import exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductLocalisationController {

    private final Logger log = LoggerFactory.getLogger(ProductLocalisationController.class);

    private final ProductLocalisationService productLocalisationService;

    public ProductLocalisationController(ProductLocalisationService productLocalisationService) {
        this.productLocalisationService = productLocalisationService;
    }


    @GetMapping("/productLocalisation/productIndex/{productIndex}")
    public ResponseEntity<List<ProductLocalisationDto>> getAllProdLocalisationsByProdIndex(@PathVariable String productIndex) {
        log.info("Rest request to get productLocalisations by productIndex {}", productIndex);
        List<ProductLocalisationDto> productLocalisationDtos = productLocalisationService.getAllByProductIndex(productIndex);
        return new ResponseEntity<>(productLocalisationDtos, HttpStatus.OK);
    }

    @GetMapping("/productLocalisation/localisationIndex/{localisationIndex}")
    public ResponseEntity<List<ProductLocalisationDto>> getAllProdLocalisationsByLocIndex(@PathVariable String localisationIndex) {
        log.info("Rest request to get productLocalisations by localisationIndex {}", localisationIndex);
        List<ProductLocalisationDto> productLocalisationDtos = productLocalisationService.getAllByLocalisationIndex(localisationIndex);
        return new ResponseEntity<>(productLocalisationDtos, HttpStatus.OK);
    }

    @GetMapping("/productLocalisation")
    public ResponseEntity<List<ProductLocalisationDto>> getAllProductLocalisations() {
        log.info("Rest request to get all productLocalisations");
        List<ProductLocalisationDto> ProductLocalisationDtoList = productLocalisationService.getAll();
        return new ResponseEntity<>(ProductLocalisationDtoList, HttpStatus.OK);
    }

    @PostMapping("/productLocalisation")
    public ResponseEntity<ProductLocalisationDto> createProductLocalisation(@Valid @RequestBody ProductLocalisationDto productLocalisationDto) {
        log.info("Rest request to save productLocalisation {}", productLocalisationDto);
        if (productLocalisationDto.getId() != null) {
            throw new BadRequestException("New product localisation cannot already have an ID");
        }

        ProductLocalisationDto savedProductLocalisation = productLocalisationService.save(productLocalisationDto);
        return new ResponseEntity<>(savedProductLocalisation, HttpStatus.CREATED);
    }

    @PutMapping("/productLocalisation")
    public ResponseEntity<ProductLocalisationDto> updateProductLocalisation(@Valid @RequestBody ProductLocalisationDto productLocalisationDto) {
        log.info("Rest request to update productLocalisation {}", productLocalisationDto);
        if (productLocalisationDto.getId() == null) {
            throw new BadRequestException("Invalid id");
        }

        ProductLocalisationDto updatedProductLocalisation = productLocalisationService.save(productLocalisationDto);
        return new ResponseEntity<>(updatedProductLocalisation, HttpStatus.OK);
    }

    @DeleteMapping("/productLocalisation")
    public ResponseEntity<Void> deleteProductLocalisation(@RequestBody ProductLocalisationDto productLocalisationDto) {
        log.info("Rest request to delete productLocalisation {}", productLocalisationDto);
        productLocalisationService.delete(productLocalisationDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
