package com.kris.warsztat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class consult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate visitDate;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mech_id")
    private mechanik mechanik;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pojazd_id")
    private pojazd pojazd;

    @Override
    public String toString () {
        return String.format("%d     %s     %s     %s    %s",
                this.id, this.visitDate.toString(), this.description, mechanik, pojazd);
    }

}
