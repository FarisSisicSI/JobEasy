package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Intervju;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Services.IntervjuService;
import projekat.jobeasy.Services.PrijavaService;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/intervju")
public class IntervjuController {

    private final PrijavaService prijavaService;
    private final IntervjuService intervjuService;

    @Autowired
    public IntervjuController(PrijavaService prijavaService, IntervjuService intervjuService) {
        this.prijavaService = prijavaService;
        this.intervjuService = intervjuService;
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
    public String sacuvajIntervju(@RequestParam Long prijavaId,
                                  @RequestParam LocalDate datum,
                                  @RequestParam LocalTime vrijeme,
                                  @RequestParam String mjesto,
                                  @RequestParam String komentar) {

        Prijava prijava = prijavaService.findById(prijavaId);
        if (prijava == null) {
            return "redirect:/error";
        }

        Intervju intervju = new Intervju();
        intervju.setDatum(datum);
        intervju.setVrijeme(vrijeme);
        intervju.setMjesto(mjesto);
        intervju.setKomentar(komentar);
        intervju.setKorisnik(prijava.getKorisnik()); // Povezivanje korisnika
        intervju.setPrijava(prijava); // Povezivanje sa prijavom

        intervjuService.save(intervju);

        return "redirect:/welcome";
    }
}
