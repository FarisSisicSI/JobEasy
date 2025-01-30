package projekat.jobeasy.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Firma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    private String naziv;

    @NotNull
    @Size(min = 3)
    private String adresa;

    @ManyToOne
    @JoinColumn(name = "id_opcine", nullable = false) // Foreign key
    private Opcina opcina;

    @NotNull
    @Size(min = 3)
    private String kontaktOsoba;

    @NotNull
    @Size(min = 3)
    private String telefon;

    @NotNull
    @Size(min = 3)
    private String mobilni;

    @NotNull
    @Email
    private String email;

    public Firma() {}

    public Firma(Long id, String naziv, String adresa, Opcina opcina, String kontaktOsoba, String telefon, String mobilni, String email) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.opcina = opcina;
        this.kontaktOsoba = kontaktOsoba;
        this.telefon = telefon;
        this.mobilni = mobilni;
        this.email = email;
    }
}
