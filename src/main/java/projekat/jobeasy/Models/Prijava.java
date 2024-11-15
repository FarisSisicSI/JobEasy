package projekat.jobeasy.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Prijava {

    @Getter
    @Setter
    @jakarta.persistence.Id
    @org.springframework.data.annotation.Id
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
    private String cv;

    @ManyToMany
    @JoinTable(
            name = "pozicija_prijava",
            joinColumns = @JoinColumn(name = "prijava_id"),
            inverseJoinColumns = @JoinColumn(name = "pozicija_id")
    )
    private List<Pozicije> upisanePozicije = new ArrayList<>();

    // Constructors
    public Prijava() {}

    public Prijava(Long id, String ime, String email, String prezime, String cv) {
        this.id = id;
        this.ime = ime;
        this.email = email;
        this.prezime = prezime;
        this.cv = cv;
    }

}
