package com.falcon.warehouse.bootstrap;

import com.falcon.warehouse.dto.LocalisationDto;
import com.falcon.warehouse.dto.ProductDto;
import com.falcon.warehouse.service.LocalisationService;
import com.falcon.warehouse.service.ProductLocalisationService;
import com.falcon.warehouse.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WarehouseBootstrap implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(WarehouseBootstrap.class);

    private final ProductService productService;
    private final LocalisationService localisationService;
    private final ProductLocalisationService productLocalisationService;

    public WarehouseBootstrap (ProductService productService, LocalisationService localisationService, ProductLocalisationService productLocalisationService) {

        this.productService = productService;
        this.localisationService = localisationService;
        this.productLocalisationService = productLocalisationService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productService.getAll().size() == 0 || productService.getAll() == null) {
            log.debug("Bootstrapping data on start up");
            loadData();
        } else {
            log.debug("Skipping bootstrap data");
        }
    }

    private void loadData() {

        //Adding product with one localisation
        ProductDto potato = new ProductDto();
        potato.setName("Potato");
        potato.setProductIndex("V001SPKG");
        potato.setQuantity(new BigDecimal("100.00"));
        potato = productService.save(potato);


        LocalisationDto vegetables = new LocalisationDto();
        vegetables.setLocalisationName("Warehouse With Vegetables");
        vegetables.setLocalisationIndex("M01R01P03V");
        vegetables = localisationService.save(vegetables);

        productService.addLocalisation(potato.getProductIndex(), vegetables, new BigDecimal("100.00"));

        //Adding product with many localisation
        ProductDto apple = new ProductDto();
        apple.setName("Apple");
        apple.setProductIndex("V002SAKG");
        apple.setQuantity(new BigDecimal("200.00"));
        apple = productService.save(apple);

        LocalisationDto fruits = new LocalisationDto();
        fruits.setLocalisationName("Warehouse With Fruits");
        fruits.setLocalisationIndex("M01R01P03F");
        fruits = localisationService.save(fruits);

        LocalisationDto fruits2 = new LocalisationDto();
        fruits2.setLocalisationName("Warehouse With Fruits");
        fruits2.setLocalisationIndex("M01R02P03F");
        fruits2 = localisationService.save(fruits2);

        LocalisationDto fruits3 = new LocalisationDto();
        fruits3.setLocalisationName("Warehouse With Fruits");
        fruits3.setLocalisationIndex("M01R03P03F");
        fruits3 = localisationService.save(fruits3);

        productService.addLocalisation(apple.getProductIndex(), fruits, new BigDecimal("49.50"));
        productService.addLocalisation(apple.getProductIndex(), fruits2, new BigDecimal("49.50"));
        productService.addLocalisation(apple.getProductIndex(), fruits3, new BigDecimal("100.00"));
        //Adding localisation with many products

        LocalisationDto sweets = new LocalisationDto();
        sweets.setLocalisationName("Warehouse With Sweets");
        sweets.setLocalisationIndex("M01R22P01S");
        sweets = localisationService.save(sweets);

        ProductDto drops = new ProductDto();
        drops.setQuantity(new BigDecimal("332.00"));
        drops.setName("Drops");
        drops.setProductIndex("V012SDSZT");
        drops = productService.save(drops);

        ProductDto chocolate = new ProductDto();
        chocolate.setQuantity(new BigDecimal("123.00"));
        chocolate.setName("Chocolate");
        chocolate.setProductIndex("V013SCSZT");
        chocolate = productService.save(chocolate);

        ProductDto waffles = new ProductDto();
        waffles.setQuantity(new BigDecimal("897.00"));
        waffles.setName("Waffles");
        waffles.setProductIndex("V014SWSZT");
        waffles = productService.save(waffles);

        localisationService.addProduct(sweets.getLocalisationIndex(), drops, new BigDecimal("332.00"));
        localisationService.addProduct(sweets.getLocalisationIndex(), chocolate, new BigDecimal("123.00"));
        localisationService.addProduct(sweets.getLocalisationIndex(), waffles, new BigDecimal("897.00"));
    }

}
