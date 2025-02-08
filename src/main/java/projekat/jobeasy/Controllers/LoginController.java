package projekat.jobeasy.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String login(@RequestParam(name = "message", required = false) String message, Model model) {
        if (message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        return "login";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        // Onemogućavanje keširanja kako bi se sprečilo vraćanje na stranicu nakon odjave
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return "redirect:/login?logout";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, HttpServletResponse response) {
        // Onemogućavanje keširanja kako bi korisnik morao ponovo da se prijavi ako se izlogovao
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        // Provera da li je korisnik autentifikovan
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        // Dodavanje statističkih podataka na model
        model.addAttribute("ukupanBrojPozicija", pozicijaService.countAll());
        model.addAttribute("ukupanBrojKorisnika", korisnikService.countAll());
        model.addAttribute("ukupanBrojFirmi", firmaService.countAll());
        model.addAttribute("pozicije", pozicijaService.pronadjiSveOtvorenePozicije());

        return "welcome";
    }
}
