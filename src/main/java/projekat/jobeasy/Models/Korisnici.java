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
public class Korisnici {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    private String ime;

    @NotNull
    @Size(min = 3)
    private String prezime;

    @NotNull
    private String adresa;

    @NotNull
    @Size(min = 9, max = 15)
    private String telefon;

    private String vozackaDozvola;

    private String cv;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 3)
    private String username;

    @NotNull
    @Size(min = 3)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_mjesto", nullable = false)
    private Opcina opcina;

    @ManyToOne
    @JoinColumn(name = "id_zanimanja1", nullable = false)
    private Zanimanje zanimanje1;

    @ManyToOne
    @JoinColumn(name = "id_zanimanja2")
    private Zanimanje zanimanje2;

    private Integer idRole = 1;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean enabled = false;

    @OneToOne(mappedBy = "korisnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private VerificationToken verificationToken;

    public Korisnici() {}

    public Korisnici(Long id, String ime, String prezime, String adresa, String telefon, String vozackaDozvola,
                     String cv, String email, String username, String password, Opcina opcina, Zanimanje zanimanje1,
                     Zanimanje zanimanje2, Integer idRole) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.telefon = telefon;
        this.vozackaDozvola = vozackaDozvola;
        this.cv = cv;
        this.email = email;
        this.username = username;
        this.password = password;
        this.opcina = opcina;
        this.zanimanje1 = zanimanje1;
        this.zanimanje2 = zanimanje2;
        this.idRole = idRole != null ? idRole : 1;
    }
}
