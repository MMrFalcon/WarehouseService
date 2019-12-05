package com.falcon.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"localisation_id", "product_id"}))
@Data
public class ProductLocalisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productLocalisation_id")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private BigDecimal quantityInLocalisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("productLocalisations")
    @JoinColumn(name = "localisation_id")
    private Localisation localisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("productLocalisations")
    @JoinColumn(name = "product_id")
    private Product product;
}
