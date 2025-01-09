package projekat.jobeasy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekat.jobeasy.Models.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    void deleteByKorisnikId(Long korisnikId);
}
