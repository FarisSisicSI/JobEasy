package projekat.jobeasy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Models.Prijava;

import java.util.List;

@Repository
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {
    boolean existsByKorisnikAndPozicija(Korisnici korisnik, Pozicije pozicija);
    boolean existsByKorisnikIdAndPozicijaId(Long korisnikId, Long pozicijaId);
    List<Prijava> findByPozicijaId(Long pozicijaId);
    List<Prijava> findByKorisnik(Korisnici korisnik);

}
