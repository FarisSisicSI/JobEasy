package projekat.jobeasy.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Zanimanje;



@Repository
public interface ZanimanjeRepository extends JpaRepository<Zanimanje, Long> {




}
