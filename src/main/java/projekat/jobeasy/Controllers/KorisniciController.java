package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Opcina;
import projekat.jobeasy.Models.Zanimanje;
import projekat.jobeasy.Services.KorisnikService;
import projekat.jobeasy.Services.VerificationTokenService;
import projekat.jobeasy.Services.OpcinaService;
import projekat.jobeasy.Services.ZanimanjeService;

import java.util.List;

@Controller
@RequestMapping("/korisnici")
public class KorisniciController {

    private final KorisnikService korisnikService;
    private final VerificationTokenService verificationTokenService;
    private final OpcinaService opcinaService;
    private final ZanimanjeService zanimanjeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public KorisniciController(KorisnikService korisnikService, VerificationTokenService verificationTokenService,
                               OpcinaService opcinaService, ZanimanjeService zanimanjeService) {
        this.korisnikService = korisnikService;
        this.verificationTokenService = verificationTokenService;
        this.opcinaService = opcinaService;
        this.zanimanjeService = zanimanjeService;
    }

    // Prikaz liste korisnika
    @GetMapping
    public String listaKorisnika(Model model) {
        model.addAttribute("korisnici", korisnikService.pronadjiSveKorisnike());
        return "korisnik_list";
    }

    // Brisanje korisnika
    @GetMapping("/delete/{korisnikId}")
    public String brisanjeKorisnika(@PathVariable Long korisnikId) {
        korisnikService.izbrisiKorisnika(korisnikId);
        verificationTokenService.deleteTokensByKorisnikId(korisnikId);
        return "redirect:/korisnici";
    }

    // Prikaz forme za editovanje korisnika
    @GetMapping("/edit/{korisnikId}")
    public String editovanjeKorisnika(@PathVariable Long korisnikId, Model model) {
        korisnikService.pronadjiKorisnikaId(korisnikId).ifPresent(korisnik -> model.addAttribute("korisnik", korisnik));

        // Dodaj liste opcina i zanimanja za dropdown menije
        List<Opcina> opcine = opcinaService.pronadjiSveOpcine();
        List<Zanimanje> zanimanja = zanimanjeService.pronadjiSvaZanimanja();

        model.addAttribute("opcine", opcine);
        model.addAttribute("zanimanja", zanimanja);

        return "korisnik_edit";
    }

    // Čuvanje izmenjenog korisnika
    @PostMapping("/edit/{korisnikId}")
    public String sacuvajKorisnika(@PathVariable Long korisnikId, @ModelAttribute Korisnici korisnik) {
        korisnikService.pronadjiKorisnikaId(korisnikId).ifPresent(postojeciKorisnik -> {
            postojeciKorisnik.setIme(korisnik.getIme());
            postojeciKorisnik.setPrezime(korisnik.getPrezime());
            postojeciKorisnik.setAdresa(korisnik.getAdresa());
            postojeciKorisnik.setTelefon(korisnik.getTelefon());
            postojeciKorisnik.setVozackaDozvola(korisnik.getVozackaDozvola());
            postojeciKorisnik.setCvFile(korisnik.getCvFile());
            postojeciKorisnik.setEmail(korisnik.getEmail());
            postojeciKorisnik.setUsername(korisnik.getUsername());

            // Ako korisnik menja lozinku, enkriptuj je
            if (korisnik.getPassword() != null && !korisnik.getPassword().isEmpty()) {
                postojeciKorisnik.setPassword(passwordEncoder.encode(korisnik.getPassword()));
            }

            // Ažuriranje opcine i zanimanja ako su dostupni
            if (korisnik.getOpcina() != null) {
                postojeciKorisnik.setOpcina(korisnik.getOpcina());
            }
            if (korisnik.getZanimanje1() != null) {
                postojeciKorisnik.setZanimanje1(korisnik.getZanimanje1());
            }
            if (korisnik.getZanimanje2() != null) {
                postojeciKorisnik.setZanimanje2(korisnik.getZanimanje2());
            }

            korisnikService.sacuvajKorisnika(postojeciKorisnik);
        });
        return "redirect:/korisnici";
    }
}
