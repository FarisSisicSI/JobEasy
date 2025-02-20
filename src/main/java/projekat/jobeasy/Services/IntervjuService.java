package projekat.jobeasy.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekat.jobeasy.Models.Intervju;
import projekat.jobeasy.Repositories.IntervjuRepository;

@Service
public class IntervjuService {

    @Autowired
    private IntervjuRepository intervjuRepository;

    public void save(Intervju intervju) {
        intervjuRepository.save(intervju);
    }
}
