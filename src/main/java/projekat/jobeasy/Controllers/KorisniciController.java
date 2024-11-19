package projekat.jobeasy.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Services.KorisnikService;


@Controller
@RequestMapping("/korisnici")
public class KorisniciController {

    private final KorisnikService korisnikService;

    public KorisniciController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @GetMapping
    public String listaKorisnika(Model model) {
        model.addAttribute("korisnici", korisnikService.pronadjiSveKorisnike());
        return "korisnik_list";
    }

    @GetMapping("/registracija")
    public String registracijaForma(Model model) {
        model.addAttribute("korisnik", new Korisnici());
        return "registracija";
    }

    @PostMapping("/registracija")
    public String dodajKorisnika(@ModelAttribute Korisnici korisnici) {
        if (korisnici.getIdRole() == null) {
            korisnici.setIdRole(1);
        }
        korisnikService.sacuvajKorisnika(korisnici);
        return "redirect:/welcome";
    }


    @GetMapping("/delete/{korisnikId}")
    public String brisanjeKorisnika(@PathVariable Long korisnikId) {
        korisnikService.izbrisiKorisnika(korisnikId);
        return "redirect:/korisnici";
    }

    @GetMapping("/edit/{korisnikId}")
    public String editovanjeKorisnika(@PathVariable Long korisnikId, Model model) {
        korisnikService.pronadjiKorisnikaId(korisnikId).ifPresent(korisnik -> model.addAttribute("korisnik", korisnik));
        return "korisnik_edit";
    }


    @PostMapping("/edit/{korisnikId}")
    public String sacuvajKorisnika(@PathVariable Long korisnikId, @ModelAttribute Korisnici korisnik) {
        korisnikService.pronadjiKorisnikaId(korisnikId).ifPresent(postojeciKorisnik -> {
            postojeciKorisnik.setIme(korisnik.getIme());
            postojeciKorisnik.setPrezime(korisnik.getPrezime());
            postojeciKorisnik.setEmail(korisnik.getEmail());
            postojeciKorisnik.setUsername(korisnik.getUsername());
            postojeciKorisnik.setPassword(korisnik.getPassword());

            korisnikService.sacuvajKorisnika(postojeciKorisnik);
        });
        return "redirect:/korisnici";
    }



    }