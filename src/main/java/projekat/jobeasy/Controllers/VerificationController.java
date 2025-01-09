package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.VerificationToken;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Repositories.VerificationTokenRepository;

@RestController
public class VerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private KorisniciRepository userRepository;

    @GetMapping("/api/v1/verify")
    public String verifyAccount(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "Invalid or expired token.";
        }

        Korisnici korisnik = verificationToken.getKorisnik();
        korisnik.setEnabled(true);
        userRepository.save(korisnik);

        return "Account verified successfully!";
    }
}

