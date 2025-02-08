package projekat.jobeasy.Controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Repositories.KorisniciRepository;
import projekat.jobeasy.Services.AutentifikacijaService;
import projekat.jobeasy.Services.OpcinaService;
import projekat.jobeasy.Services.VerificationTokenService;
import projekat.jobeasy.Services.ZanimanjeService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/registracija")
public class RegistracijaController {

    @Autowired
    private KorisniciRepository korisniciRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService tokenService;

    @Autowired
    private AutentifikacijaService emailService;

    @Autowired
    private ZanimanjeService zanimanjeService;

    @Autowired
    private OpcinaService opcinaService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/cvprijave/";

    @GetMapping
    public String registracijaForma(Model model) {
        model.addAttribute("korisnik", new Korisnici());
        model.addAttribute("opcine", opcinaService.pronadjiSveOpcine());
        model.addAttribute("zanimanja", zanimanjeService.pronadjiSvaZanimanja());
        return "registracija";
    }

    @PostMapping
    public String dodajKorisnika(
            @ModelAttribute Korisnici korisnici,
            Model model
    ) throws MessagingException, IOException {

        // Provera da li korisnik već ima CV
        if (korisnici.getCvFile() != null && !korisnici.getCvFile().isEmpty()) {
            model.addAttribute("error", "Već ste dodali CV i ne možete ga ponovo dodati.");
            return "registracija";
        }

        // Šifrovanje lozinke
        korisnici.setPassword(passwordEncoder.encode(korisnici.getPassword()));

        // Postavi podrazumevanu ulogu ako nije specificirana
        if (korisnici.getIdRole() == null) {
            korisnici.setIdRole(1);
        }

        // Prvo sačuvaj korisnika u bazi
        korisnici = korisniciRepository.save(korisnici);

        // Obrada CV fajla ako nije već dodat
        if (korisnici.getCv() != null && !korisnici.getCv().isEmpty()) {
            MultipartFile cvFile = korisnici.getCv();
            String originalFilename = cvFile.getOriginalFilename();
            String fileName = korisnici.getUsername() + "_" + originalFilename;
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            // Kreiraj direktorijum ako ne postoji
            Files.createDirectories(filePath.getParent());

            // Sačuvaj fajl
            Files.copy(cvFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Ažuriraj korisnika sa imenom fajla i ponovo ga sačuvaj
            korisnici.setCvFile(fileName);
            korisniciRepository.save(korisnici);
        }

        // Generiši token i pošalji email
        String token = tokenService.generateVerificationToken(korisnici);
        String verificationLink = "http://localhost:8080/api/v1/verify?token=" + token;

        emailService.sendEmail(korisnici.getEmail(), "Verifikacija naloga", verificationLink);

        model.addAttribute("message", "Registracija uspešna! Proverite vaš email za verifikaciju.");
        return "redirect:/login?message=" + URLEncoder.encode("Registracija uspešna! Proverite vaš email za verifikaciju.", StandardCharsets.UTF_8);


    }


}
