package projekat.jobeasy.Controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Services.AutentifikacijaService;
import projekat.jobeasy.Services.VerificationTokenService;

@Controller
public class RegistracijaController {

    @Autowired
    private KorisniciRepository korisniciRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService tokenService;

    @Autowired
    private AutentifikacijaService emailService;

    @GetMapping("/registracija")
    public String registracijaForma(Model model) {
        model.addAttribute("korisnik", new Korisnici());
        return "registracija";
    }

    @PostMapping("/registracija")
    public String dodajKorisnika(Korisnici korisnici, Model model) throws MessagingException {
        // Enkodovanje lozinke
        korisnici.setPassword(passwordEncoder.encode(korisnici.getPassword()));

        // Postavi default ID role ako nije definisano
        if (korisnici.getIdRole() == null) {
            korisnici.setIdRole(1);
        }

        // Sačuvaj korisnika u bazi
        korisniciRepository.save(korisnici);

        // Generiši token za verifikaciju
        String token = tokenService.generateVerificationToken(korisnici);

        // Kreiraj verifikacioni link
        String verificationLink = "http://localhost:8080/api/v1/verify?token=" + token;






        emailService.sendEmail(
                korisnici.getEmail(),
                "Verifikacija naloga",
                 verificationLink
        );

        // Dodaj poruku o uspešnom registrovanju
        model.addAttribute("message", "Registracija uspešna! Proverite vaš email za verifikaciju.");
        return "redirect:/login";
    }
}
