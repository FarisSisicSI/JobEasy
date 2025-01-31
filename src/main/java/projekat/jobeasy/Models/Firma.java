package projekat.jobeasy.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

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

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 3")
    private Integer idRole;

    public Firma() {}

    public Firma(String naziv, String adresa, Opcina opcina, String kontaktOsoba, String telefon, String mobilni, String email) {
        this.naziv = naziv;
        this.adresa = adresa;
        this.opcina = opcina;
        this.kontaktOsoba = kontaktOsoba;
        this.telefon = telefon;
        this.mobilni = mobilni;
        this.email = email;
        this.username = generirajUsername();
        this.password = encodePassword(generirajjednostavnusifru());
        this.idRole = 3;
    }



    private String generirajUsername() {
        return naziv.replaceAll("\\s+", "").toLowerCase();
    }

    private String encodePassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPassword);
    }

    private String generirajjednostavnusifru() {
        Random random = new Random();
        int randomNum = 1000 + random.nextInt(9000);
        return naziv.replaceAll("\\s+", "").toLowerCase() + randomNum;
    }
}
