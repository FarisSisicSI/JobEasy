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
    @Email
    private String email;

    @NotNull
    @Size(min = 3)
    private String username;

    @NotNull
    @Size(min = 3)
    private String password;

    private Integer idRole = 1;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean enabled = false;

    @OneToOne(mappedBy = "korisnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private VerificationToken verificationToken;



    public Korisnici() {}

    public Korisnici(Long id, String ime, String prezime, String email, String username, String password, Integer idRole) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.username = username;
        this.password = password;
        this.idRole = idRole != null ? idRole : 1;

    }
}
