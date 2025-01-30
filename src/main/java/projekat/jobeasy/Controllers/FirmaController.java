package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Firma;
import projekat.jobeasy.Services.FirmaService;
import projekat.jobeasy.Services.OpcinaService;


@Controller
@RequestMapping("/firma")
public class FirmaController {

    private final FirmaService firmaService;
    private final OpcinaService opcinaService;

    @Autowired
    public FirmaController(FirmaService firmaService, OpcinaService opcinaService) {
        this.firmaService = firmaService;
        this.opcinaService = opcinaService;
    }

    @GetMapping
    public String listaFirmi(Model model) {
        model.addAttribute("firme", firmaService.pronadjiSveFirme());
        return "firma_list";
    }

    @GetMapping("/delete/{firmaId}")
    public String brisanjeFirme(@PathVariable Long firmaId) {
        firmaService.izbrisiFirmu(firmaId);
        return "redirect:/firme";
    }

    @GetMapping("/edit/{firmaId}")
    public String editovanjeFirme(@PathVariable Long firmaId, Model model) {
        firmaService.pronadjiFirmuId(firmaId).ifPresent(firma -> model.addAttribute("firma", firma));
        return "firma_edit";
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

            firmaService.sacuvajFirmu(postojecaFirma);
        });
        return "redirect:/firme";
    }

    @GetMapping("/novafirma")
    public String prikaziFormuZaDodavanje(Model model) {
        model.addAttribute("firma", new Firma());

        model.addAttribute("opcine", opcinaService.pronadjiSveOpcine()); // Dodaj listu opcina
        return "firma_dodaj";
    }


    @PostMapping("/novafirma")
    public String sacuvajFirmu(@ModelAttribute("firma") Firma firma) {
        firmaService.sacuvajFirmu(firma);
        return "redirect:/firma"; // Nakon dodavanja, vraća na listu firmi
    }
}
