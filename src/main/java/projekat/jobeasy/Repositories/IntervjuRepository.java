package projekat.jobeasy.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Intervju;

@Repository
public interface IntervjuRepository extends JpaRepository<Intervju, Long> {
}

