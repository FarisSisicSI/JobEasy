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
import projekat.jobeasy.Security.CustomUserDetails;
import projekat.jobeasy.Services.FirmaService;
import projekat.jobeasy.Services.KorisnikService;
import projekat.jobeasy.Services.PozicijaService;
import projekat.jobeasy.Services.PrijavaService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final PozicijaService pozicijaService;
    private final KorisnikService korisnikService;
    private final FirmaService firmaService;
    private final PrijavaService prijavaService;

    public LoginController(PozicijaService pozicijaService, KorisnikService korisnikService, FirmaService firmaService, PrijavaService prijavaService) {
        this.pozicijaService = pozicijaService;
        this.korisnikService = korisnikService;
        this.firmaService = firmaService;
        this.prijavaService = prijavaService;
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


        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return "redirect:/login?logout";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        var optionalFirma = firmaService.pronadjiFirmuUsername(username);

        Long korisnikId = null;
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            korisnikId = userDetails.getId(); // Dobijamo ID korisnika
        }

        if (optionalFirma.isPresent()) {
            model.addAttribute("pozicije", pozicijaService.findByFirma(optionalFirma.get()));
        } else {
            var svePozicije = pozicijaService.pronadjiSveOtvorenePozicije();
            model.addAttribute("pozicije", svePozicije);
            model.addAttribute("ukupanBrojPozicija", pozicijaService.countAll());
            model.addAttribute("ukupanBrojKorisnika", korisnikService.countAll());
            model.addAttribute("ukupanBrojFirmi", firmaService.countAll());

            if (korisnikId != null) {
                Map<Long, Boolean> prijavljenePozicije = new HashMap<>();
                for (var pozicija : svePozicije) {
                    prijavljenePozicije.put(pozicija.getId(), prijavaService.korisnikVecPrijavljen(korisnikId, pozicija.getId()));
                }
                model.addAttribute("prijavljenePozicije", prijavljenePozicije);
            }
        }

        return "welcome";
    }



}
