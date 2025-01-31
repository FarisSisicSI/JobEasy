package projekat.jobeasy.Services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Firma;
import projekat.jobeasy.Repositories.FirmaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FirmaService {

    private final FirmaRepository firmaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public FirmaService(FirmaRepository firmaRepository) {
        this.firmaRepository = firmaRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Firma> pronadjiSveFirme() {
        return firmaRepository.findAll();
    }

    public Optional<Firma> pronadjiFirmuId(Long id) {
        return firmaRepository.findById(id);
    }

    public void izbrisiFirmu(Long id) {
        if (firmaRepository.existsById(id)) {
            firmaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Firma sa ID " + id + " ne postoji.");
        }
    }

    public Firma sacuvajFirmu(Firma firma) {
        // Postavi podrazumevane vrednosti ako nisu unete
        if (firma.getUsername() == null || firma.getUsername().isBlank()) {
            firma.setUsername(firma.getNaziv().replaceAll("\\s+", "").toLowerCase());
        }
        if (firma.getPassword() == null || firma.getPassword().isBlank()) {
            firma.setPassword(passwordEncoder.encode(firma.getNaziv().replaceAll("\\s+", "").toLowerCase() + "1234"));
        } else if (!firma.getPassword().startsWith("$2a$")) { // Provera da li je već šifrovana
            firma.setPassword(passwordEncoder.encode(firma.getPassword()));
        }
        if (firma.getIdRole() == null) {
            firma.setIdRole(3);
        }

        return firmaRepository.save(firma);
    }

    public long countAll() {
        return firmaRepository.count();
    }

    public Optional<Firma> pronadjiFirmuUsername(String username) {
        return firmaRepository.findByUsername(username);
    }
}
