package projekat.jobeasy.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Firma;
@Repository

public interface FirmaRepository extends JpaRepository<Firma, Long> {
}
