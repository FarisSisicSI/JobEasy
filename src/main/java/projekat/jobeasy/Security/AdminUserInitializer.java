package projekat.jobeasy.Security;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Repositories.KorisniciRepository;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private KorisniciRepository korisniciRepository; // Replace with your actual repository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (korisniciRepository.findByUsername("admin").isEmpty()) {
            Korisnici admin = new Korisnici();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Default password
            admin.setIme("Admin");  // Dodaj ime
            admin.setPrezime("Adminic"); // Dodaj prezime
            admin.setEmail("admin@example.com");  // Dodaj email
            admin.setIdRole(2);
            korisniciRepository.save(admin);
            System.out.println("Kreiran admin: 'admin' and lozinka: 'admin123'");
        } else {
            System.out.println("Admin vec postoji!");
        }

    }
}
