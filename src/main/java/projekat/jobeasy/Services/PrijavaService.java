package projekat.jobeasy.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Repositories.PozicijaRepository;
import projekat.jobeasy.Repositories.PrijavaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrijavaService {

    @Autowired
    private PrijavaRepository prijavaRepository;

    @Autowired
    private PozicijaRepository pozicijaRepository;

    @Autowired
    private KorisniciRepository korisniciRepository;

    public List<Prijava> pronadjiSvePrijave() {
        return prijavaRepository.findAll();
    }

    public Optional<Prijava> pronadjiPrijavuPoId(Long id) {
        return prijavaRepository.findById(id);
    }

    public Prijava spasiPrijavu(Long korisnikId, Long pozicijaId) {
        Korisnici korisnik = korisniciRepository.findById(korisnikId)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronađen"));

        Pozicije pozicija = pozicijaRepository.findById(pozicijaId)
                .orElseThrow(() -> new EntityNotFoundException("Pozicija nije pronađena"));

        Prijava prijava = new Prijava(korisnik, pozicija);
        return prijavaRepository.save(prijava);
    }

    public void izbrisiPrijavu(Long id) {
        if (!prijavaRepository.existsById(id)) {
            throw new EntityNotFoundException("Prijava sa ID-em " + id + " nije pronađena.");
        }
        prijavaRepository.deleteById(id);
    }


    public List<Prijava> pronadjiPrijaveZaPoziciju(Long pozicijaId) {
        return prijavaRepository.findByPozicijaId(pozicijaId);
    }

    public boolean korisnikVecPrijavljen(Long korisnikId, Long pozicijaId) {
        return prijavaRepository.existsByKorisnikIdAndPozicijaId(korisnikId, pozicijaId);
    }

    public List<Prijava> findByKorisnik(Korisnici korisnik) {
        return prijavaRepository.findByKorisnik(korisnik);
    }


}
