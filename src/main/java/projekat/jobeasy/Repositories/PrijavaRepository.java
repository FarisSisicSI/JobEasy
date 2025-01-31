package projekat.jobeasy.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Prijava;



@Repository
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {




}
