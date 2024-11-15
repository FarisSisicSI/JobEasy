package projekat.jobeasy.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projekat.jobeasy.Models.Prijava;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrijavaRepository extends JpaRepository<Prijava, Long> {

//    private List<Prijava> prijave = new ArrayList<>();
//    private Long trenutniId = 1L;
//
//    public List<Prijava> findAll() {
//        return prijave;
//    }
//
//    public Optional<Prijava> findById(Long id) {
//        return prijave.stream().filter(prijava -> prijava.getId().equals(id)).findFirst();
//    }
//
//    public Prijava save(Prijava prijava) {
//        prijava.setId(trenutniId++);
//        prijave.add(prijava);
//        return prijava;
//    }


}
