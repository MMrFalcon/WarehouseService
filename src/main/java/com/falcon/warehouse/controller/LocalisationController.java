package com.falcon.warehouse.controller;

import com.falcon.warehouse.dto.LocalisationDto;
import com.falcon.warehouse.dto.ProductDto;
import com.falcon.warehouse.service.LocalisationService;
import exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LocalisationController {

    private final Logger log = LoggerFactory.getLogger(LocalisationController.class);

    private final LocalisationService localisationService;

    public LocalisationController(LocalisationService localisationService) {
        this.localisationService = localisationService;
    }

    @GetMapping("/localisation/index/{localisationIndex}")
    public ResponseEntity<LocalisationDto> getLocalisationByIndex(@PathVariable String localisationIndex) {
        log.info("Rest request to get localisation by index {}", localisationIndex);
        LocalisationDto localisationDto = localisationService.getByLocalisationIndex(localisationIndex);
        return new ResponseEntity<>(localisationDto, HttpStatus.OK);
    }

    @GetMapping("/localisation")
    public ResponseEntity<List<LocalisationDto>> getAllLocalisations() {
        log.info("Rest request to get all localisations");
        List<LocalisationDto> localisationDtoList = localisationService.getAll();
        return new ResponseEntity<>(localisationDtoList, HttpStatus.OK);
    }

    @PostMapping("/localisation")
    public ResponseEntity<LocalisationDto> createLocalisation(@Valid @RequestBody LocalisationDto localisationDto) {
        log.info("Rest request to save localisation {}", localisationDto);
        if (localisationDto.getId() != null) {
            throw new BadRequestException("New localisation cannot already have an ID");
        }

        LocalisationDto savedLocalisation = localisationService.save(localisationDto);
        return new ResponseEntity<>(savedLocalisation, HttpStatus.CREATED);
    }

    @PutMapping("/localisation")
    public ResponseEntity<LocalisationDto> updateLocalisation(@Valid @RequestBody LocalisationDto localisationDto) {
        log.info("Rest request to update localisation {}", localisationDto);
        if (localisationDto.getId() == null) {
            throw new BadRequestException("Invalid id");
        }

        LocalisationDto updatedLocalisation = localisationService.save(localisationDto);
        return new ResponseEntity<>(updatedLocalisation, HttpStatus.OK);
    }

    @PutMapping("/localisation/add/product")
    public ResponseEntity<LocalisationDto> addProduct(@RequestParam(value = "localisationIndex") String localisationIndex,
                                                      @RequestParam(value = "quantity") String quantity,
                                                      @RequestBody ProductDto productDto) {

        log.info("Rest request to add product to localisation {}", localisationIndex);
        LocalisationDto updatedLocalisation = localisationService.addProduct(localisationIndex, productDto, new BigDecimal(quantity));
        return new ResponseEntity<>(updatedLocalisation, HttpStatus.OK);
    }

    @DeleteMapping("/localisation")
    public ResponseEntity<Void> deleteLocalisation(@RequestBody LocalisationDto localisationDto) {
        log.info("Rest request to delete localisation {}", localisationDto);
        localisationService.delete(localisationDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
