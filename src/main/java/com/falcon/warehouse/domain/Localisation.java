package com.falcon.warehouse.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "localisation", cascade = CascadeType.ALL)
    private Set<ProductLocalisation> productLocalisations = new HashSet<>();

    @Builder
    public Localisation(Long id, @NotNull String localisationIndex, @NotNull String localisationName, Set<ProductLocalisation> productLocalisations) {
        this.id = id;
        this.localisationIndex = localisationIndex;
        this.localisationName = localisationName;
        this.productLocalisations = productLocalisations;
    }
}
