package projekat.jobeasy.Services;

import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Firma;
import projekat.jobeasy.Repositories.FirmaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FirmaService {

    private final FirmaRepository firmaRepository;

    public FirmaService(FirmaRepository firmaRepository) {
        this.firmaRepository = firmaRepository;
    }

    public List<Firma> pronadjiSveFirme() {
        return firmaRepository.findAll();
    }

    public Optional<Firma> pronadjiFirmuId(Long id) {
        return firmaRepository.findById(id);
    }

    public void izbrisiFirmu(Long id) {
        firmaRepository.deleteById(id);
    }

    public Firma sacuvajFirmu(Firma firma) {
        return firmaRepository.save(firma);
    }
}
