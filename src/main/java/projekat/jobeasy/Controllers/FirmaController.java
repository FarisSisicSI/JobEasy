package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Firma;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Security.CustomUserDetails;
import projekat.jobeasy.Services.FirmaService;
import projekat.jobeasy.Services.OpcinaService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import projekat.jobeasy.Services.PozicijaService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/firma")
public class FirmaController {

    private final FirmaService firmaService;
    private final OpcinaService opcinaService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PozicijaService pozicijaService;
    @Autowired
    public FirmaController(FirmaService firmaService, OpcinaService opcinaService, PozicijaService pozicijaService) {
        this.firmaService = firmaService;
        this.opcinaService = opcinaService;
        this.pozicijaService = pozicijaService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping
    public String listaFirmi(Model model) {
        model.addAttribute("firme", firmaService.pronadjiSveFirme());
        return "firma_list";
    }

    @GetMapping("/delete/{firmaId}")
    public String brisanjeFirme(@PathVariable Long firmaId) {
        firmaService.izbrisiFirmu(firmaId);
        return "redirect:/firma";
    }

    @GetMapping("/edit/{firmaId}")
    public String editovanjeFirme(@PathVariable Long firmaId, Model model) {
        Optional<Firma> firma = firmaService.pronadjiFirmuId(firmaId);
        if (firma.isPresent()) {
            model.addAttribute("firma", firma.get());
            model.addAttribute("opcine", opcinaService.pronadjiSveOpcine()); // Dodaj opcine u model
            return "firma_edit";
        }
        return "redirect:/firma";
    }

    @PostMapping("/edit/{firmaId}")
    public String sacuvajFirmu(@PathVariable Long firmaId, @ModelAttribute Firma firma) {
        firmaService.pronadjiFirmuId(firmaId).ifPresent(postojecaFirma -> {
            postojecaFirma.setNaziv(firma.getNaziv());
            postojecaFirma.setAdresa(firma.getAdresa());
            postojecaFirma.setOpcina(firma.getOpcina());
            postojecaFirma.setKontaktOsoba(firma.getKontaktOsoba());
            postojecaFirma.setTelefon(firma.getTelefon());
            postojecaFirma.setMobilni(firma.getMobilni());
            postojecaFirma.setEmail(firma.getEmail());

            // Proveri da li je lozinka promenjena pre nego što je šifrujemo
            if (!firma.getPassword().isBlank()) {
                postojecaFirma.setPassword(passwordEncoder.encode(firma.getPassword()));
            }

            firmaService.sacuvajFirmu(postojecaFirma);
        });
        return "redirect:/firma";
    }

    @GetMapping("/novafirma")
    public String prikaziFormuZaDodavanje(Model model) {
        model.addAttribute("firma", new Firma());
        model.addAttribute("opcine", opcinaService.pronadjiSveOpcine());
        return "firma_dodaj";
    }

    @PostMapping("/novafirma")
    public String sacuvajFirmu(@ModelAttribute("firma") Firma firma) {
        // Postavi podrazumevane vrednosti ako nisu unete
        if (firma.getUsername() == null || firma.getUsername().isBlank()) {
            firma.setUsername(firma.getNaziv().replaceAll("\\s+", "").toLowerCase());
        }
        if (firma.getPassword() == null || firma.getPassword().isBlank()) {
            firma.setPassword(passwordEncoder.encode(firma.getNaziv().replaceAll("\\s+", "").toLowerCase() + "1234"));
        }
        if (firma.getIdRole() == null) {
            firma.setIdRole(3);
        }

        firmaService.sacuvajFirmu(firma);
        return "redirect:/firma";
    }

    @GetMapping("/profil")
    public String prikaziProfilFirme(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return "redirect:/login";
        }

        Long firmaId = userDetails.getId();
        Firma firma = firmaService.pronadjiFirmuId(firmaId).orElse(null);

        if (firma == null) {
            return "redirect:/login";
        }
        List<Pozicije> objavljenePozicije = pozicijaService.findByFirma(firma);
        model.addAttribute("firma", firma);
        model.addAttribute("objavljenePozicije", objavljenePozicije);

        return "firma_view";
    }

}
