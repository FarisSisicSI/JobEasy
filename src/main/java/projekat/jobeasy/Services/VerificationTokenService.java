package projekat.jobeasy.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.VerificationToken;
import projekat.jobeasy.Repositories.VerificationTokenRepository;

import java.util.UUID;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.tokenRepository = verificationTokenRepository;
    }

    public String generateVerificationToken(Korisnici korisnik) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, korisnik);
        tokenRepository.save(verificationToken);
        return token;
    }

    public void deleteTokensByKorisnikId(Long korisnikId) {
        tokenRepository.deleteByKorisnikId(korisnikId);
    }
}
