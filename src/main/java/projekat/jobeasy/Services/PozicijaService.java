package projekat.jobeasy.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Repositories.PozicijaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PozicijaService {

    private final PozicijaRepository pozicijaRepository;

    @Autowired
    public PozicijaService(PozicijaRepository pozicijaRepository) {
        this.pozicijaRepository = pozicijaRepository;
    }

    public List<Pozicije> pronadjiSveOtvorenePozicije() {
        return pozicijaRepository.findAll();
    }

    public Optional<Pozicije> pronadjiPozicijuId(Long id) {
        return pozicijaRepository.findById(id);
    }

    public Pozicije sacuvajPoziciju(Pozicije pozicija) {
        return pozicijaRepository.save(pozicija);
    }

    public long countAll() {
        long broj = pozicijaRepository.count();
        return broj;
    }



    public List<Pozicije> findAll() {
        return pozicijaRepository.findAll();
    }

    public void izbrisiPoziciju(Long id) {
        pozicijaRepository.deleteById(id);
    }
}
