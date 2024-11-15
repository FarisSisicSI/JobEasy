package projekat.jobeasy.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Korisnici;
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
    public String dodajPoziciju(@ModelAttribute Korisnici korisnici) {
        korisnikService.sacuvajKorisnika(korisnici);
        return "redirect:/welcome";
    }

    @GetMapping("/delete/{korisnikId}")
    public String brisanjeKorisnika(@PathVariable Long korisnikId) {
        korisnikService.izbrisiKorisnika(korisnikId);
        return "redirect:/korisnici";
    }


    }