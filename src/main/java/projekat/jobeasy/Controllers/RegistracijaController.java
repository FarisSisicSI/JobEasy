package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Repositories.KorisniciRepository;

@Controller
public class RegistracijaController {

    @Autowired
    private KorisniciRepository korisniciRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registracija")
    public String registracijaForma(Model model) {
        model.addAttribute("korisnik", new Korisnici());
        return "registracija";
    }

    @PostMapping("/registracija")
    public String dodajKorisnika(Korisnici korisnici, Model model) {

        korisnici.setPassword(passwordEncoder.encode(korisnici.getPassword()));
        if (korisnici.getIdRole() == null) {
            korisnici.setIdRole(1);
        }
        korisniciRepository.save(korisnici);
        return "redirect:/login";
    }

}
