package projekat.jobeasy.Controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projekat.jobeasy.Models.Intervju;
import projekat.jobeasy.Models.Korisnici;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Services.AutentifikacijaService;
import projekat.jobeasy.Services.IntervjuService;
import projekat.jobeasy.Services.KorisnikService;
import projekat.jobeasy.Services.PrijavaService;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/intervju")
public class IntervjuController {
    @Autowired
    private AutentifikacijaService emailService;

    private final PrijavaService prijavaService;
    private final IntervjuService intervjuService;
    private final KorisnikService korisniciService;

    @Autowired
    public IntervjuController(PrijavaService prijavaService, IntervjuService intervjuService, KorisnikService korisniciService) {
        this.prijavaService = prijavaService;
        this.intervjuService = intervjuService;
        this.korisniciService = korisniciService;
    }

    @GetMapping("/zakazi/{prijavaId}")
    public String prikaziFormuZaIntervju(@PathVariable Long prijavaId, Model model) {
        Prijava prijava = prijavaService.findById(prijavaId);
        if (prijava == null) {
            return "redirect:/error";
        }
        model.addAttribute("prijava", prijava);
        model.addAttribute("intervju", new Intervju());
        return "intervju_forma";
    }

    @PostMapping("/sacuvaj")
    public String zakaziIntervju(@RequestParam Long prijavaId,
                                 @RequestParam String datum,
                                 @RequestParam Long korisnikId,
                                 @RequestParam String vrijeme,
                                 @RequestParam String mjesto,
                                 @RequestParam String komentar,
                                 RedirectAttributes redirectAttributes) throws MessagingException {

        Korisnici korisnik = korisniciService.findById(korisnikId);
        if (korisnikId == null) {
            redirectAttributes.addFlashAttribute("error", "Nije prosleđen ID korisnika.");
            return "redirect:/intervju/zakazi/" + prijavaId;
        }

        Prijava prijava = prijavaService.findById(prijavaId);
        Intervju intervju = new Intervju(
                prijava,
                korisnik,
                LocalDate.parse(datum),  // datum mora biti LocalDate
                LocalTime.parse(vrijeme),  // vrijeme mora biti LocalTime
                mjesto,
                komentar
        );

        intervjuService.save(intervju);

        String kandidatEmail = prijava.getKorisnik().getEmail();
        String firmaEmail = prijava.getFirma().getEmail();
        String kandidatIme = prijava.getKorisnik().getIme() + " " + prijava.getKorisnik().getPrezime();

        // E-mail za korisnika
        emailService.sendInterviewEmail(kandidatEmail, "Zakazan intervju", kandidatIme, datum, vrijeme, mjesto, komentar, false);

        // E-mail za firmu
        emailService.sendInterviewEmail(firmaEmail, "Potvrda zakazanog intervjua", kandidatIme, datum, vrijeme, mjesto, komentar, true);

        redirectAttributes.addFlashAttribute("success", "Intervju je uspešno zakazan i e-mailovi su poslati.");
        return "redirect:/welcome";
    }

}
