package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Repositories.PozicijaRepository;

import projekat.jobeasy.Repositories.PrijavaRepository;
import projekat.jobeasy.Services.PozicijaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class PrijavaController {

    @Autowired
    private PrijavaRepository prijavaRepository;

    @Autowired
    private PozicijaRepository pozicijeRepository;

    @Autowired
    private KorisniciRepository korisnikRepository;

    @Autowired
    private PozicijaService pozicijaService;

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

    @GetMapping("/{pozicijaId}")
    public String pregledPozicije(@PathVariable Long pozicijaId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Pozicije> optionalPozicija = pozicijaService.pronadjiPozicijuId(pozicijaId);

        if (optionalPozicija.isPresent()) {
            Pozicije pozicija = optionalPozicija.get();
            model.addAttribute("pozicija", pozicija);

            // Proveravamo prijave korisnika
            Korisnici korisnik = korisnikRepository.findByUsername(userDetails.getUsername())
                    .orElse(null);

            Map<Long, Boolean> prijavljenePozicije = new HashMap<>();
            if (korisnik != null) {
                List<Prijava> korisnikovePrijave = prijavaRepository.findByKorisnik(korisnik);
                for (Prijava prijava : korisnikovePrijave) {
                    prijavljenePozicije.put(prijava.getPozicija().getId(), true);
                }
            }

            // Ako je mapa prazna, dodajemo je u model da ne bude null
            model.addAttribute("prijavljenePozicije", prijavljenePozicije.isEmpty() ? new HashMap<>() : prijavljenePozicije);
        }

        return "pozicija_pregled";
    }

    @PostMapping("/prijave/ponistiprijavu")
    public String ponistiPrijavu(@RequestParam Long pozicijaId, @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return "redirect:/welcome?error=Morate biti prijavljeni da biste poništili prijavu";
        }

        Korisnici korisnik = korisnikRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

        Pozicije pozicija = pozicijeRepository.findById(pozicijaId)
                .orElseThrow(() -> new RuntimeException("Pozicija ne postoji"));

        Optional<Prijava> prijava = prijavaRepository.findByKorisnikAndPozicija(korisnik, pozicija);

        prijava.ifPresent(prijavaRepository::delete);

        return "redirect:/welcome?success=Uspjesno ste ponistili prijavu!";
    }

    @PostMapping("/prijave/prihvati/{prijavaId}")
    public String prihvatiPrijavu(@PathVariable Long prijavaId) {
        Prijava prijava = prijavaRepository.findById(prijavaId)
                .orElseThrow(() -> new RuntimeException("Prijava nije pronađena"));

        prijava.setStatus(2);
        prijavaRepository.save(prijava);

        return "redirect:/intervju/zakazi/" + prijavaId;
    }




}
