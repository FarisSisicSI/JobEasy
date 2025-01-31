package projekat.jobeasy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekat.jobeasy.Models.Firma;

import java.util.Optional;

public interface FirmaRepository extends JpaRepository<Firma, Long> {
    Optional<Firma> findByUsername(String username);
}
