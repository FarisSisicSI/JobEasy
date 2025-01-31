package projekat.jobeasy.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private final FirmaRepository firmaRepository;

    @Autowired
    public KorisnikService(KorisniciRepository korisniciRepository, FirmaRepository firmaRepository) {
        this.korisniciRepository = korisniciRepository;
        this.firmaRepository = firmaRepository;
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
    @Transactional
    public void izbrisiKorisnika(Long id) {
        korisniciRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Prvo tražimo korisnika u bazi korisnika
        Optional<Korisnici> korisnikOpt = korisniciRepository.findByUsername(username);
        if (korisnikOpt.isPresent()) {
            Korisnici korisnik = korisnikOpt.get();
            if (!korisnik.isEnabled()) {
                throw new UsernameNotFoundException("Korisnički nalog nije verifikovan. Proverite svoj email.");
            }

            String role = korisnik.getIdRole() == 1 ? "USER" : "ADMIN";
            return new CustomUserDetails(
                    korisnik.getId(),  // Čuvamo ID korisnika
                    korisnik.getUsername(),
                    korisnik.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );
        }

        // Ako nije pronađen korisnik, tražimo firmu u bazi firmi
        Optional<Firma> firmaOpt = firmaRepository.findByUsername(username);
        if (firmaOpt.isPresent()) {
            Firma firma = firmaOpt.get();

            return new CustomUserDetails(
                    firma.getId(),  // Čuvamo ID firme
                    firma.getUsername(),
                    firma.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_FIRMA"))
            );
        }

        throw new UsernameNotFoundException("Nalog sa korisničkim imenom: " + username + " ne postoji.");
    }



    public long countAll() {
        long broj = korisniciRepository.count();


        return broj;
    }




}
