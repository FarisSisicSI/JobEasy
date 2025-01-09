package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public String verifyAccount(@RequestParam("token") String token) {
        // Pronađi token
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "Invalid or expired token. Please check your email for the correct verification link.";
        }


        // Proveri da li je korisnik već verifikovan
        Korisnici korisnik = verificationToken.getKorisnik();
        if (korisnik.isEnabled()) {
            return "Your account is already verified. You can log in.";
        }

        // Verifikuj korisnički nalog
        korisnik.setEnabled(true);
        userRepository.save(korisnik);

        return "Your account has been successfully verified! You can now log in.";
    }
}
