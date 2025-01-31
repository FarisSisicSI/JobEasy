package projekat.jobeasy.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Zanimanje;
import projekat.jobeasy.Repositories.ZanimanjeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ZanimanjeService {

    private final ZanimanjeRepository zanimanjeRepository;

    @Autowired
    public ZanimanjeService(ZanimanjeRepository zanimanjeRepository) {
        this.zanimanjeRepository = zanimanjeRepository;
    }

    public List<Zanimanje> pronadjiSvaZanimanja() {
        return zanimanjeRepository.findAll();
    }

    public Optional<Zanimanje> pronadjiZanimanjePoId(Long id) {
        return zanimanjeRepository.findById(id);
    }

    public Zanimanje sacuvajZanimanje(Zanimanje zanimanje) {
        return zanimanjeRepository.save(zanimanje);
    }

    public void izbrisiZanimanje(Long id) {
        if (zanimanjeRepository.existsById(id)) {
            zanimanjeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Zanimanje sa ID " + id + " ne postoji.");
        }
    }
}
