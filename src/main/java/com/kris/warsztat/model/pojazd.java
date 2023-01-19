package com.kris.warsztat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class pojazd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rejestracja;
    private LocalDate przegladDate;
    private Boolean isFaktura;
    private String ownerName;

    @OneToMany(mappedBy = "pojazd",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<consult> consultSet = new HashSet<>();

    public void addConsult(consult consult) {
        consultSet.add(consult);
        consult.setPojazd(this);
    }

    public void removeConsult(consult consult) {
        consultSet.remove(consult);
        consult.setPojazd(null);
    }

    public String isFakturaStringValue() {
        if(isFaktura) {
            return "Tak";
        }
        return "Nie";
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %s", this.rejestracja, this.id, this.przegladDate);
    }
}
