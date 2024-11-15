package projekat.jobeasy.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Repositories.PozicijaRepository;
import projekat.jobeasy.Repositories.PrijavaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PrijavaService {

    @Autowired
    private PrijavaRepository prijavaRepository;

    @Autowired
    private PozicijaRepository pozicijaRepository; // For handling course associations
    @Autowired
    private PozicijaService pozicijaService;

    public List<Prijava> pronadjiSvePrijave() {
        return prijavaRepository.findAll();
    }

    public Optional<Prijava> pronadjiPrijavuPoId(Long id) {
        return prijavaRepository.findById(id);
    }

    public Prijava spasiPrijavu(Prijava prijava, List<Long> pozicijaIds) {
        if (pozicijaIds != null) {
            for (Long pozicijaId : pozicijaIds) {
                pozicijaService.pronadjiPozicijuId(pozicijaId).ifPresent(prijava.getUpisanePozicije()::add);
            }
        }
        return prijavaRepository.save(prijava);
    }


    public Prijava UpdatePrijava(Prijava prijava, List<Long> pozicijeIds) {
        prijava.getUpisanePozicije().clear();
        if (pozicijeIds != null) {
            for (Long pozicijaId : pozicijeIds) {
                pozicijaService.pronadjiPozicijuId(pozicijaId).ifPresent(prijava.getUpisanePozicije()::add);
            }
        }
        return prijavaRepository.save(prijava);
    }

    public void izbrisiPrijavu(Long id) {
        prijavaRepository.deleteById(id);
    }
}
