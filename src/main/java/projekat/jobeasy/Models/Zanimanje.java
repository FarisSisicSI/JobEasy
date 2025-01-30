package projekat.jobeasy.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Zanimanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    private String naziv;

    @ManyToOne
    @JoinColumn(name = "id_spreme", nullable = false)
    private Sprema sprema;

    public Zanimanje() {}
    public Zanimanje(Long id, String naziv, Sprema sprema) {
        this.sprema = sprema;
        this.id = id;
        this.naziv = naziv;

    }
}
