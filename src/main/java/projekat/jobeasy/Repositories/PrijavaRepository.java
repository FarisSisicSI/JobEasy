package projekat.jobeasy.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Prijava;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {




}
