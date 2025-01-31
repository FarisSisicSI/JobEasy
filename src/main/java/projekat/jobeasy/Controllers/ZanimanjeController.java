package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Zanimanje;
import projekat.jobeasy.Services.ZanimanjeService;

import java.util.List;

@Controller
@RequestMapping("/zanimanja")
public class ZanimanjeController {

    private final ZanimanjeService zanimanjeService;

    @Autowired
    public ZanimanjeController(ZanimanjeService zanimanjeService) {
        this.zanimanjeService = zanimanjeService;
    }

    @GetMapping
    public String listaZanimanja(Model model) {
        model.addAttribute("zanimanja", zanimanjeService.pronadjiSvaZanimanja());
        return "zanimanje_list";
    }

    @GetMapping("/{id}")
    public String pregledZanimanja(@PathVariable Long id, Model model) {
        zanimanjeService.pronadjiZanimanjePoId(id)
                .ifPresent(zanimanje -> model.addAttribute("zanimanje", zanimanje));
        return "zanimanje_pregled";
    }

    @GetMapping("/novozanimanje")
    public String formaZaDodavanje(Model model) {
        model.addAttribute("zanimanje", new Zanimanje());
        return "zanimanje_dodaj";
    }

    @PostMapping("/novozanimanje")
    public String dodajZanimanje(@ModelAttribute Zanimanje zanimanje) {
        zanimanjeService.sacuvajZanimanje(zanimanje);
        return "redirect:/zanimanja";
    }

    @GetMapping("/edit/{id}")
    public String formaZaIzmenu(@PathVariable Long id, Model model) {
        zanimanjeService.pronadjiZanimanjePoId(id)
                .ifPresent(zanimanje -> model.addAttribute("zanimanje", zanimanje));
        return "zanimanje_edit";
    }

    @PostMapping("/edit/{id}")
    public String sacuvajIzmene(@PathVariable Long id, @ModelAttribute Zanimanje zanimanje) {
        zanimanjeService.pronadjiZanimanjePoId(id).ifPresent(izmenjeno -> {
            izmenjeno.setNaziv(zanimanje.getNaziv());
            izmenjeno.setSprema(zanimanje.getSprema());
            zanimanjeService.sacuvajZanimanje(izmenjeno);
        });
        return "redirect:/zanimanja";
    }

    @GetMapping("/delete/{id}")
    public String obrisiZanimanje(@PathVariable Long id) {
        zanimanjeService.izbrisiZanimanje(id);
        return "redirect:/zanimanja";
    }
}
