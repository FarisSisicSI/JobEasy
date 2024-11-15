package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Services.PozicijaService;

import java.util.List;

@Controller
@RequestMapping("/pozicije")
public class PozicijeController {

    private final PozicijaService pozicijaService;

    @Autowired
    public PozicijeController(PozicijaService pozicijaService) {
        this.pozicijaService = pozicijaService;
    }

    @GetMapping
    public String listaPozicija(Model model) {
        model.addAttribute("pozicije", pozicijaService.pronadjiSveOtvorenePozicije());
        return "pozicija_list";
    }

    @GetMapping("/{pozicijaId}")
    public String pregledPozicije(@PathVariable Long pozicijaId, Model model) {
        pozicijaService.pronadjiPozicijuId(pozicijaId).ifPresent(pozicija -> model.addAttribute("pozicija", pozicija));
        return "pozicija_pregled";
    }

    @GetMapping("/edit/{pozicijaId}")
    public String editovanjePozicije(@PathVariable Long pozicijaId, Model model) {
        pozicijaService.pronadjiPozicijuId(pozicijaId).ifPresent(pozicija -> model.addAttribute("pozicija", pozicija));
        return "pozicija_edit";
    }

    @PostMapping("/edit/{pozicijaId}")
    public String sacuvajPozicijuEditovanu(@PathVariable Long pozicijaId, @ModelAttribute Pozicije pozicija) {
        pozicijaService.pronadjiPozicijuId(pozicijaId).ifPresent(postojecaPozicija -> {
            postojecaPozicija.setNazivPozicije(pozicija.getNazivPozicije());
            postojecaPozicija.setKvalifikacije(pozicija.getKvalifikacije());
            postojecaPozicija.setOpisPozicije(pozicija.getOpisPozicije());
            postojecaPozicija.setOdgovornosti(pozicija.getOdgovornosti());
            pozicijaService.sacuvajPoziciju(postojecaPozicija); // dodaj poziv za snimanje
        });
        return "redirect:/pozicije";
    }

    @GetMapping("/delete/{pozicijaId}")
    public String brisanjePozicije(@PathVariable Long pozicijaId) {
        pozicijaService.izbrisiPoziciju(pozicijaId);
        return "redirect:/pozicije";
    }

    @GetMapping("/novapozicija")
    public String pozicijaForma(Model model) {
        model.addAttribute("pozicija", new Pozicije());
        return "pozicija_dodaj";
    }

    @PostMapping("/novapozicija")
    public String dodajPoziciju(@ModelAttribute Pozicije pozicija) {
        pozicijaService.sacuvajPoziciju(pozicija);
        return "redirect:/pozicije";
    }
}
