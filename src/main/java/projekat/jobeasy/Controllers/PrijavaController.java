package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Services.PozicijaService;
import projekat.jobeasy.Services.PrijavaService;

import java.util.List;


@Controller
@RequestMapping("/prijava")
public class PrijavaController {

    @Autowired
    private PozicijaService pozicijaService;
    @Autowired
    private PrijavaService prijavaService;

    @GetMapping
    public String listaPrijava(Model model) {
        model.addAttribute("prijava", prijavaService.pronadjiSvePrijave());
        return "prijava_pregled";
    }



    @GetMapping("/novaprijava")
    public String prijavaNaPoziciju(Model model) {
        model.addAttribute("prijava", new Prijava());
        model.addAttribute("pozicije", pozicijaService.pronadjiSveOtvorenePozicije());
        return "prijava_na_poziciju";
    }

    @PostMapping("/novaprijava")
    public String dodajPrijavu(@ModelAttribute Prijava prijava, @RequestParam(value = "pozicijaIds", required = false) List<Long> pozicijaIds) {
        prijavaService.spasiPrijavu(prijava, pozicijaIds);
        return "redirect:/prijava";
    }

    @GetMapping("/edit/{id}")
    public String prikaziEditPrijave(@PathVariable Long id, Model model) {
        prijavaService.pronadjiPrijavuPoId(id).ifPresent(prijava -> model.addAttribute("prijava", prijava));
        model.addAttribute("pozicije", pozicijaService.pronadjiSveOtvorenePozicije());
        return "prijava_edit";
    }

    @PostMapping("/edit")
    public String editPrijavu(@ModelAttribute Prijava prijava, @RequestParam(value = "pozicijaIds", required = false) List<Long> pozicijaIds) {
        prijavaService.UpdatePrijava(prijava, pozicijaIds);
        return "redirect:/prijava";
    }

    @GetMapping("/delete/{id}")
    public String izbrisiPrijavu(@PathVariable Long id) {
        prijavaService.izbrisiPrijavu(id);
        return "redirect:/prijava";
    }






}
