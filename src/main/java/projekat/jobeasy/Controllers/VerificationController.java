package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.VerificationToken;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Repositories.VerificationTokenRepository;

import java.time.LocalDateTime;

@RestController
public class VerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private KorisniciRepository userRepository;

    @GetMapping("/api/v1/verify")
    public RedirectView verifyAccount(@RequestParam("token") String token) {
        // Pronađi token
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            RedirectView redirectView = new RedirectView("/login");
            redirectView.addStaticAttribute("message", "Nevažeći ili istekao token.");
            return redirectView;
        }

        // Proveri da li je korisnik već verifikovan
        Korisnici korisnik = verificationToken.getKorisnik();
        if (korisnik.isEnabled()) {
            RedirectView redirectView = new RedirectView("/login");
            redirectView.addStaticAttribute("message", "Vaš račun je već verifikovan! Možete se prijaviti.");
            return redirectView;
        }

        // Verifikuj korisnički nalog
        korisnik.setEnabled(true);
        userRepository.save(korisnik);

        RedirectView redirectView = new RedirectView("/login");
        redirectView.addStaticAttribute("message", "Uspješno ste verifikovali svoj račun! Sada se možete prijaviti!");
        return redirectView;
    }
}
