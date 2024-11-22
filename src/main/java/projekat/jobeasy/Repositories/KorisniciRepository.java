package projekat.jobeasy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekat.jobeasy.Models.Korisnici;

import java.util.Optional;

public interface KorisniciRepository extends JpaRepository<Korisnici, Long> {
    Optional<Korisnici> findByUsername(String username);


}
