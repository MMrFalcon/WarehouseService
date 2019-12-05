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

    @OneToMany(mappedBy = "localisation")
    private Set<ProductLocalisation> productLocalisations = new HashSet<>();

    @Builder
    public Localisation(@NotNull String localisationIndex, @NotNull String localisationName, Set<ProductLocalisation> productLocalisations) {
        this.localisationIndex = localisationIndex;
        this.localisationName = localisationName;
        this.productLocalisations = productLocalisations;
    }
}
