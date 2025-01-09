package projekat.jobeasy.Models;

import jakarta.persistence.*;
import projekat.jobeasy.Models.Korisnici;

import java.time.LocalDateTime;

@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Korisnici korisnik;

    private LocalDateTime expiryDate;

    // Konstruktor, getteri i setteri
    public VerificationToken(String token, Korisnici korisnik) {
        this.token = token;
        this.korisnik = korisnik;
        this.expiryDate = LocalDateTime.now().plusHours(24); // Token važi 24 sata
    }

    public Korisnici getKorisnik(){
        return korisnik;
    }

    public VerificationToken() {}
}
