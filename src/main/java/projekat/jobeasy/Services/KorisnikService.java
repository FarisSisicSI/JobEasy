package projekat.jobeasy.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Firma;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Repositories.FirmaRepository;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Security.CustomUserDetails;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService implements UserDetailsService {

    private final KorisniciRepository korisniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final FirmaRepository firmaRepository;

    @Autowired
    public KorisnikService(KorisniciRepository korisniciRepository, PasswordEncoder passwordEncoder, FirmaRepository firmaRepository) {
        this.korisniciRepository = korisniciRepository;
        this.passwordEncoder = passwordEncoder;
        this.firmaRepository = firmaRepository;
    }

    public List<Korisnici> pronadjiSveKorisnike() {
        return korisniciRepository.findAll();
    }

    public Optional<Korisnici> pronadjiKorisnikaId(Long id) {
        return korisniciRepository.findById(id);
    }

    public void izbrisiKorisnika(Long id) {
        korisniciRepository.deleteById(id);
    }

    public Korisnici sacuvajKorisnika(Korisnici korisnik) {
        return korisniciRepository.save(korisnik);
    }

    @Transactional
    public void azurirajKorisnika(Long korisnikId, Korisnici noviPodaci) {
        korisniciRepository.findById(korisnikId).ifPresent(postojeciKorisnik -> {
            postojeciKorisnik.setIme(noviPodaci.getIme());
            postojeciKorisnik.setPrezime(noviPodaci.getPrezime());
            postojeciKorisnik.setAdresa(noviPodaci.getAdresa());
            postojeciKorisnik.setTelefon(noviPodaci.getTelefon());
            postojeciKorisnik.setVozackaDozvola(noviPodaci.getVozackaDozvola());
            postojeciKorisnik.setCv(noviPodaci.getCv());
            postojeciKorisnik.setEmail(noviPodaci.getEmail());
            postojeciKorisnik.setUsername(noviPodaci.getUsername());

            if (noviPodaci.getPassword() != null && !noviPodaci.getPassword().isEmpty()) {
                postojeciKorisnik.setPassword(passwordEncoder.encode(noviPodaci.getPassword()));
            }

            if (noviPodaci.getOpcina() != null) {
                postojeciKorisnik.setOpcina(noviPodaci.getOpcina());
            }
            if (noviPodaci.getZanimanje1() != null) {
                postojeciKorisnik.setZanimanje1(noviPodaci.getZanimanje1());
            }
            if (noviPodaci.getZanimanje2() != null) {
                postojeciKorisnik.setZanimanje2(noviPodaci.getZanimanje2());
            }

            korisniciRepository.save(postojeciKorisnik);
        });
    }


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Prvo pokušaj da nađeš korisnika
            Optional<Korisnici> korisnikOpt = korisniciRepository.findByUsername(username);
            if (korisnikOpt.isPresent()) {
                Korisnici korisnik = korisnikOpt.get();
                return new CustomUserDetails(
                        korisnik.getId(),
                        korisnik.getUsername(),
                        korisnik.getPassword(),
                        korisnik.getIdRole()
                );
            }

            // Ako korisnik ne postoji, pokušaj da pronađeš firmu
            Optional<Firma> firmaOpt = firmaRepository.findByUsername(username);
            if (firmaOpt.isPresent()) {
                Firma firma = firmaOpt.get();
                return new CustomUserDetails(
                        firma.getId(),
                        firma.getUsername(),
                        firma.getPassword(),
                        firma.getIdRole()
                );
            }

            throw new UsernameNotFoundException("Korisnik ili firma sa korisničkim imenom " + username + " nije pronađen.");
        }
    


    public long countAll() {
        return korisniciRepository.count();
    }
}
