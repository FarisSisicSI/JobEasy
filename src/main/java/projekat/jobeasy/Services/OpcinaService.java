package projekat.jobeasy.Services;

import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Opcina;
import projekat.jobeasy.Repositories.OpcinaRepository;

import java.util.List;

@Service
public class OpcinaService {
    private final OpcinaRepository opcinaRepository;

    public OpcinaService(OpcinaRepository opcinaRepository) {
        this.opcinaRepository = opcinaRepository;
    }

    public List<Opcina> pronadjiSveOpcine() {
        return opcinaRepository.findAll();
    }
}
