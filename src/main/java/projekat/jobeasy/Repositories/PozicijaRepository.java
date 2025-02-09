package projekat.jobeasy.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Firma;
import projekat.jobeasy.Models.Pozicije;
@Repository

public interface PozicijaRepository extends JpaRepository<Pozicije, Long> {
    List<Pozicije> findByFirma(Firma firma);
}
