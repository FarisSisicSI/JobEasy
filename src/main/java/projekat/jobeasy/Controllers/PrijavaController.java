package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Repositories.PozicijaRepository;

import projekat.jobeasy.Repositories.PrijavaRepository;

@Controller
public class PrijavaController {

    @Autowired
    private PrijavaRepository prijavaRepository;

    @Autowired
    private PozicijaRepository pozicijeRepository;

    @Autowired
    private KorisniciRepository korisnikRepository;

    @PostMapping("/prijave/novaprijava")
    public String prijaviSeNaPoziciju(@RequestParam Long pozicijaId,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        Korisnici korisnik = korisnikRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));


        Pozicije pozicija = pozicijeRepository.findById(pozicijaId)
                .orElseThrow(() -> new RuntimeException("Pozicija ne postoji"));


        boolean vecPrijavljen = prijavaRepository.existsByKorisnikAndPozicija(korisnik, pozicija);
        if (vecPrijavljen) {
            return "redirect:/welcome?error=Vec ste prijavljeni na ovu poziciju";
        }


        Prijava prijava = new Prijava(korisnik, pozicija);
        prijavaRepository.save(prijava);

        return "redirect:/welcome?success=Prijava uspesna";
    }


}
