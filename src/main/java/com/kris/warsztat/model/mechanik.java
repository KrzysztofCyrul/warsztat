package com.kris.warsztat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class mechanik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String speciality;

    @OneToMany(mappedBy = "mechanik",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<consult> consultSet = new HashSet<>();

    public void addConsult(consult consult) {
        consultSet.add(consult);
        consult.setMechanik(this);
    }

    public void removeConsult(consult consult) {
        consultSet.remove(consult);
        consult.setMechanik(null);
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
