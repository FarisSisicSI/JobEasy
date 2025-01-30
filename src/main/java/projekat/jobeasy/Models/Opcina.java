package projekat.jobeasy.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Opcina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    private String naziv;

    @NotNull
    @Size(min = 5)
    private Long postanskiBroj;

    public Opcina() {}
    public Opcina(Long id, String naziv, Long postanskiBroj) {
        this.id = id;
        this.naziv = naziv;
        this.postanskiBroj = postanskiBroj;
    }
}
