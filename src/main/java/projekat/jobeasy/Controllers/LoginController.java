package projekat.jobeasy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projekat.jobeasy.Services.PozicijaService;

@Controller
public class LoginController {

    private final PozicijaService pozicijaService;

    @Autowired
    public LoginController(PozicijaService pozicijaService) {
        this.pozicijaService = pozicijaService;
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
    public String welcome( Model model) {
        long ukupanBrojPozicija = pozicijaService.countAll();
        model.addAttribute("ukupanBrojPozicija", ukupanBrojPozicija);

        model.addAttribute("pozicije", pozicijaService.pronadjiSveOtvorenePozicije());
        return "welcome";
    }

}
