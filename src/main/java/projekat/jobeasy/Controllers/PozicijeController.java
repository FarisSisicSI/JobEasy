package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekat.jobeasy.Models.Firma;
import projekat.jobeasy.Models.Pozicije;
import projekat.jobeasy.Models.Prijava;
import projekat.jobeasy.Services.FirmaService;
import projekat.jobeasy.Services.PozicijaService;
import projekat.jobeasy.Services.PrijavaService;
import projekat.jobeasy.Services.ZanimanjeService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pozicije")
public class PozicijeController {

    private final PozicijaService pozicijaService;
    private final FirmaService firmaService;
    private final ZanimanjeService zanimanjeService;
    private final PrijavaService prijavaService;

    @Autowired
    public PozicijeController(PozicijaService pozicijaService, FirmaService firmaService, ZanimanjeService zanimanjeService, PrijavaService prijavaService) {
        this.pozicijaService = pozicijaService;
        this.firmaService = firmaService;
        this.zanimanjeService = zanimanjeService;
        this.prijavaService = prijavaService;
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
        pozicijaService.pronadjiPozicijuId(pozicijaId).ifPresent(pozicija -> {
            model.addAttribute("pozicija", pozicija);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            model.addAttribute("datumOdFormatted", pozicija.getDatumOd() != null ? pozicija.getDatumOd().format(formatter) : "");
            model.addAttribute("datumDoFormatted", pozicija.getDatumDo() != null ? pozicija.getDatumDo().format(formatter) : "");
        });
        return "pozicija_edit";
    }

    @PostMapping("/edit/{pozicijaId}")
    public String sacuvajPozicijuEditovanu(@PathVariable Long pozicijaId, @ModelAttribute Pozicije pozicija) {
        pozicijaService.pronadjiPozicijuId(pozicijaId).ifPresent(postojecaPozicija -> {
            postojecaPozicija.setNazivPozicije(pozicija.getNazivPozicije());
            postojecaPozicija.setKratakOpisPozicije(pozicija.getKratakOpisPozicije());
            postojecaPozicija.setFirma(pozicija.getFirma());
            postojecaPozicija.setZanimanje(pozicija.getZanimanje());
            postojecaPozicija.setDatumOd(pozicija.getDatumOd());
            postojecaPozicija.setDatumDo(pozicija.getDatumDo());
            pozicijaService.sacuvajPoziciju(postojecaPozicija);
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
        model.addAttribute("firme", firmaService.pronadjiSveFirme());
        model.addAttribute("zanimanja", zanimanjeService.pronadjiSvaZanimanja());

        return "pozicija_dodaj";
    }

    @PostMapping("/novapozicija")
    public String dodajPoziciju(@ModelAttribute Pozicije pozicija, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Ako je prijavljena firma, automatski dodeljujemo njen ID
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_FIRMA"))) {
            Firma firma = firmaService.pronadjiFirmuUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Firma nije pronađena"));
            pozicija.setFirma(firma);
        }

        // Postavljanje podrazumevanih vrednosti ako nisu unete
        if (pozicija.getDatumOd() == null) {
            pozicija.setDatumOd(LocalDate.now());
        }
        if (pozicija.getDatumDo() == null) {
            pozicija.setDatumDo(LocalDate.now().plusMonths(1));
        }
        if (pozicija.getKratakOpisPozicije() == null || pozicija.getKratakOpisPozicije().isEmpty()) {
            pozicija.setKratakOpisPozicije("Opis nije dostupan");
        }

        pozicijaService.sacuvajPoziciju(pozicija);
        return "redirect:/pozicije";
    }

    @GetMapping("/korisnici/prijavljeni/{pozicijaId}")
    public String pregledPrijavljenihKorisnika(@PathVariable Long pozicijaId, Model model) {
        // Dohvatite poziciju na temelju ID-a
        Pozicije pozicija = pozicijaService.pronadjiPozicijuId(pozicijaId).orElse(null);

        // Dohvatite sve prijave vezane za tu poziciju
        List<Prijava> prijave = prijavaService.pronadjiPrijaveZaPoziciju(pozicijaId);

        // Dodajte naziv pozicije i prijave u model
        model.addAttribute("pozicija", pozicija);
        model.addAttribute("prijave", prijave);

        return "korisnik_prijava"; // Naziv HTML datoteke
    }






}
