package com.falcon.warehouse.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String productIndex;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private BigDecimal quantity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductLocalisation> productLocalisations = new HashSet<>();

    @Builder
    public Product(Long id, @NotNull String productIndex, @NotNull String name, @NotNull BigDecimal quantity, Set<ProductLocalisation> productLocalisations) {
        this.id = id;
        this.productIndex = productIndex;
        this.name = name;
        this.quantity = quantity;
        this.productLocalisations = productLocalisations;
    }

   public void addProductLocalisation(ProductLocalisation productLocalisation) {
        this.productLocalisations.add(productLocalisation);
        productLocalisation.setProduct(this);
    }
}
