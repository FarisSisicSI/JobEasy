package projekat.jobeasy.Controllers;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Services.AutentifikacijaService;
import projekat.jobeasy.Services.OpcinaService;
import projekat.jobeasy.Services.VerificationTokenService;
import projekat.jobeasy.Services.ZanimanjeService;

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

    @Autowired
    private ZanimanjeService zanimanjeService;

    @Autowired
    private OpcinaService opcinaService;


    @GetMapping("/registracija")
    public String registracijaForma(Model model) {
        model.addAttribute("korisnik", new Korisnici());
        model.addAttribute("opcine", opcinaService.pronadjiSveOpcine());  // ✔ Dodaj opcine
        model.addAttribute("zanimanja", zanimanjeService.pronadjiSvaZanimanja());  // ✔ Dodaj zanimanja
        return "registracija";
    }


    @PostMapping("/registracija")
    public String dodajKorisnika(@ModelAttribute("korisnik") @Valid Korisnici korisnici,
                                 BindingResult result, Model model) throws MessagingException {
        System.out.println("Forma je poslata!");
        if (result.hasErrors()) {
            System.out.println("❌ Validacija nije prošla! Greške:");
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            model.addAttribute("opcine", opcinaService.pronadjiSveOpcine());
            model.addAttribute("zanimanja", zanimanjeService.pronadjiSvaZanimanja());
            return "registracija";
        }



        korisnici.setPassword(passwordEncoder.encode(korisnici.getPassword()));


        if (korisnici.getIdRole() == null) {
            korisnici.setIdRole(1);
        }

        // Sačuvaj korisnika u bazi
        try {
            korisniciRepository.save(korisnici);
            System.out.println("✅ Korisnik sačuvan u bazi!");
        } catch (Exception e) {
            System.out.println("❌ Greška pri čuvanju korisnika: " + e.getMessage());
            e.printStackTrace();
        }

        // Generiši token za verifikaciju
        String token = tokenService.generateVerificationToken(korisnici);

        // Kreiraj verifikacioni link
        String verificationLink = "http://localhost:8080/api/v1/verify?token=" + token;

        // Pošalji verifikacioni email
        try {
            emailService.sendEmail(
                    korisnici.getEmail(),
                    "Verifikacija naloga",
                    verificationLink
            );
            System.out.println("📩 Email poslat na: " + korisnici.getEmail());
        } catch (MessagingException e) {
            System.out.println("❌ Greška pri slanju emaila: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Registracija uspešna! Redirekcija na login...");
        model.addAttribute("message", "Registracija uspešna! Proverite vaš email za verifikaciju.");
        return "redirect:/login";
    }
}
