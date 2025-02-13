package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Opcina;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Models.Zanimanje;
import projekat.jobeasy.Security.CustomUserDetails;
import projekat.jobeasy.Services.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static projekat.jobeasy.Controllers.RegistracijaController.UPLOAD_DIR;

@Controller
@RequestMapping("/korisnici")
public class KorisniciController {

    private final KorisnikService korisnikService;
    private final VerificationTokenService verificationTokenService;
    private final OpcinaService opcinaService;
    private final ZanimanjeService zanimanjeService;
    private final PrijavaService prijavaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public KorisniciController(KorisnikService korisnikService, VerificationTokenService verificationTokenService,
                               OpcinaService opcinaService, ZanimanjeService zanimanjeService, PrijavaService prijavaService) {
        this.korisnikService = korisnikService;
        this.verificationTokenService = verificationTokenService;
        this.opcinaService = opcinaService;
        this.zanimanjeService = zanimanjeService;
        this.prijavaService = prijavaService;
    }

    // Prikaz liste korisnika
    @GetMapping
    public String listaKorisnika(Model model) {
        model.addAttribute("korisnici", korisnikService.pronadjiSveKorisnike());
        return "korisnik_list";
    }

    // Brisanje korisnika
    @GetMapping("/delete/{korisnikId}")
    public String brisanjeKorisnika(@PathVariable Long korisnikId) {
        korisnikService.izbrisiKorisnika(korisnikId);
        verificationTokenService.deleteTokensByKorisnikId(korisnikId);
        return "redirect:/korisnici";
    }

    // Prikaz forme za editovanje korisnika
    @GetMapping("/edit/{korisnikId}")
    public String editovanjeKorisnika(@PathVariable Long korisnikId, Model model) {
        korisnikService.pronadjiKorisnikaId(korisnikId).ifPresent(korisnik -> model.addAttribute("korisnik", korisnik));

        List<Opcina> opcine = opcinaService.pronadjiSveOpcine();
        List<Zanimanje> zanimanja = zanimanjeService.pronadjiSvaZanimanja();

        model.addAttribute("opcine", opcine);
        model.addAttribute("zanimanja", zanimanja);

        return "korisnik_edit";
    }

    // Čuvanje izmenjenog korisnika
    @PostMapping("/edit/{korisnikId}")
    public String sacuvajKorisnika(@PathVariable Long korisnikId,
                                   @ModelAttribute Korisnici korisnik,
                                   @RequestParam(value = "cv", required = false) MultipartFile cvFile) throws IOException {
        korisnikService.pronadjiKorisnikaId(korisnikId).ifPresent(postojeciKorisnik -> {
            postojeciKorisnik.setIme(korisnik.getIme());
            postojeciKorisnik.setPrezime(korisnik.getPrezime());
            postojeciKorisnik.setAdresa(korisnik.getAdresa());
            postojeciKorisnik.setTelefon(korisnik.getTelefon());
            postojeciKorisnik.setEmail(korisnik.getEmail());

            // Ako korisnik unese novu lozinku, enkriptuj je
            if (korisnik.getPassword() != null && !korisnik.getPassword().isEmpty()) {
                postojeciKorisnik.setPassword(passwordEncoder.encode(korisnik.getPassword()));
            }

            // Obrada CV fajla ako je dostavljen novi
            if (cvFile != null && !cvFile.isEmpty()) {
                try {
                    // Obriši stari CV ako postoji
                    if (postojeciKorisnik.getCvFile() != null) {
                        Path oldFilePath = Paths.get(UPLOAD_DIR, postojeciKorisnik.getCvFile());
                        Files.deleteIfExists(oldFilePath);
                    }

                    // Sačuvaj novi CV
                    String originalFilename = cvFile.getOriginalFilename();
                    String fileName = postojeciKorisnik.getUsername() + "_" + originalFilename;
                    Path filePath = Paths.get(UPLOAD_DIR, fileName);

                    // Kreiraj direktorijum ako ne postoji
                    Files.createDirectories(filePath.getParent());

                    // Sačuvaj fajl
                    Files.copy(cvFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // Ažuriraj korisnika sa novim imenom fajla
                    postojeciKorisnik.setCvFile(fileName);
                } catch (IOException e) {
                    e.printStackTrace(); // Loguj grešku
                }
            }

            if (korisnik.getOpcina() != null) {
                postojeciKorisnik.setOpcina(korisnik.getOpcina());
            }
            if (korisnik.getZanimanje1() != null) {
                postojeciKorisnik.setZanimanje1(korisnik.getZanimanje1());
            }
            if (korisnik.getZanimanje2() != null) {
                postojeciKorisnik.setZanimanje2(korisnik.getZanimanje2());
            }

            korisnikService.sacuvajKorisnika(postojeciKorisnik);
        });

        return "redirect:/welcome";
    }



    @GetMapping("/profil")
    public String prikaziProfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return "redirect:/login";
        }

        Long korisnikId = userDetails.getId();
        Korisnici korisnik = korisnikService.pronadjiKorisnikaId(korisnikId).orElse(null);

        if (korisnik == null) {
            return "redirect:/login";
        }
        List<Prijava> prijavljenePozicije = prijavaService.findByKorisnik(korisnik);
        model.addAttribute("korisnik", korisnik);
        model.addAttribute("prijavljenePozicije", prijavljenePozicije);

        return "korisnik_view";
    }



}
