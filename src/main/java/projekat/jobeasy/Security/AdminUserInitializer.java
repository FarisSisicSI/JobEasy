package projekat.jobeasy.Security;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Opcina;
import projekat.jobeasy.Models.Zanimanje;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Repositories.OpcinaRepository;
import projekat.jobeasy.Repositories.ZanimanjeRepository;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private KorisniciRepository korisniciRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OpcinaRepository opcinaRepository;

    @Autowired
    private ZanimanjeRepository zanimanjeRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (korisniciRepository.findByUsername("admin").isEmpty()) {
            Korisnici admin = new Korisnici();

            // Dodavanje podataka za admina
            admin.setIme("Admin");
            admin.setPrezime("Adminic");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Default password
            admin.setEmail("admin@example.com");  // Primer email adrese
            admin.setAdresa("Admin Street 1"); // Primer adrese
            admin.setTelefon("123456789"); // Primer broja telefona
            admin.setVozackaDozvola("B"); // Primer vozačke dozvole
            admin.setCvFile("N/A"); // Nema CV za admina


            Opcina defaultOpcina = opcinaRepository.findAll()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Opcina nije pronađena!"));
            admin.setOpcina(defaultOpcina);


            Zanimanje defaultZanimanje = zanimanjeRepository.findAll()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Zanimanje nije pronađeno!"));
            admin.setZanimanje1(defaultZanimanje);
            admin.setZanimanje2(null);

            admin.setIdRole(2);
            admin.setEnabled(true);

            korisniciRepository.save(admin);
            System.out.println("Kreiran admin: 'admin' i lozinka: 'admin123'");
        } else {
            System.out.println("Admin već postoji!");
        }
    }

}
