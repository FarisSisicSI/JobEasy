package projekat.jobeasy.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Repositories.KorisniciRepository;


import java.util.List;
import java.util.Optional;


@Service
public class KorisnikService implements UserDetailsService {


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
    @Transactional
    public void izbrisiKorisnika(Long id) {
        korisniciRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnici korisnici = korisniciRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik ne postoji"));

        String role;
        if (korisnici.getIdRole() == 1) {
            role = "USER";
        } else if (korisnici.getIdRole() == 2) {
            role = "ADMIN";
        } else {
            throw new IllegalArgumentException("Ne postoji rola: " + korisnici.getIdRole());
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(korisnici.getUsername())
                .password(korisnici.getPassword())
                .roles(role)
                .build();
    }




}
