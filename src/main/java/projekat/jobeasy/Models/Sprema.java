package projekat.jobeasy.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Sprema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    private String naziv;

    @NotNull
    @Size(min = 1)
    private String oznaka;

    public Sprema() {}

    public Sprema(String naziv, String oznaka) {
            this.naziv = naziv;
            this.oznaka = oznaka;
    }



}
