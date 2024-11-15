package projekat.jobeasy.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Repositories.KorisniciRepository;


import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {
    private final KorisniciRepository korisniciRepository;

    @Autowired
    public KorisnikService(KorisniciRepository korisniciRepository) {
        this.korisniciRepository = korisniciRepository;
    }

    public List<Korisnici> pronadjiSveKorisnike() {
        return korisniciRepository.findAll();
    }

    public Optional<Korisnici> pronadjiKorisnikaId(Long id) {
        return korisniciRepository.findById(id);
    }

    public Korisnici sacuvajKorisnika(Korisnici korisnici) {
        return korisniciRepository.save(korisnici);
    }

    public void izbrisiKorisnika(Long id) {
        korisniciRepository.deleteById(id);
    }



}
