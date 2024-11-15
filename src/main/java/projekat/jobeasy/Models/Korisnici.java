package projekat.jobeasy.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Korisnici {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String ime;
    @NotNull
    @Size(min = 3, max = 20)
    private String prezime;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 3, max = 20)
    private String username;
    @NotNull
    @Size(min = 3, max = 20)
    private String password;

    // Konstruktor, getteri i setteri
    public Korisnici() {}

    public Korisnici(Long id, String ime, String prezime, String email, String username, String password) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.username = username;
        this.password = password;
    }


}
