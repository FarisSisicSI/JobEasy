package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projekat.jobeasy.Services.FirmaService;
import projekat.jobeasy.Services.KorisnikService;
import projekat.jobeasy.Services.PozicijaService;

@Controller
public class LoginController {

    private final PozicijaService pozicijaService;

    private final KorisnikService korisnikService;
    private final FirmaService firmaService;

    public LoginController(PozicijaService pozicijaService, KorisnikService korisnikService, FirmaService firmaService) {
        this.pozicijaService = pozicijaService;
        this.korisnikService = korisnikService;
        this.firmaService = firmaService;
    }


    @GetMapping("/login")
    public String login() {
        // Return the login.html view
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Ulogovani korisnik: " + authentication.getName());
        System.out.println("Uloge korisnika: " + authentication.getAuthorities());

        long ukupanBrojPozicija = pozicijaService.countAll();
        long ukupanBrojKorisnika = korisnikService.countAll();
        long ukupanBrojFirmi = firmaService.countAll();

        model.addAttribute("ukupanBrojPozicija", ukupanBrojPozicija);
        model.addAttribute("ukupanBrojKorisnika", ukupanBrojKorisnika);
        model.addAttribute("ukupanBrojFirmi", ukupanBrojFirmi);
        model.addAttribute("pozicije", pozicijaService.pronadjiSveOtvorenePozicije());

        return "welcome";
    }

}
