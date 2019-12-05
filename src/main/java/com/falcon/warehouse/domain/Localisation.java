package com.falcon.warehouse.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Localisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "localisation_id")
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String localisationIndex;

    @NotNull
    @Column(unique = true, nullable = false)
    private String localisationName;

    @OneToMany(mappedBy = "localisation")
    private Set<ProductLocalisation> productLocalisations = new HashSet<>();
}
