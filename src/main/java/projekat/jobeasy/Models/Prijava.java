package projekat.jobeasy.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
public class Prijava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idKorisnika", nullable = false)
    private Korisnici korisnik;

    @ManyToOne
    @JoinColumn(name = "idPozicije", nullable = false)
    private Pozicije pozicija;

    @Column(nullable = false)
    private LocalDate datumPrijave;

    @Column(nullable = false)
    private Integer status; // Novo polje

    public Prijava() {
        this.datumPrijave = LocalDate.now();
        this.status = 1;
    }

    public Prijava(Korisnici korisnik, Pozicije pozicija) {
        this.korisnik = korisnik;
        this.pozicija = pozicija;
        this.datumPrijave = LocalDate.now();
        this.status = 1;
    }
}
