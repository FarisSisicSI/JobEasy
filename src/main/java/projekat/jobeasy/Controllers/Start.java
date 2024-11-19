package projekat.jobeasy.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Start {

    @GetMapping("/")
    public String home() {
        return "welcome";
    }

    @GetMapping("/welcome")
    public String hello(Model model) {
        return "welcome";
    }
    @GetMapping("/korisnik")
    public String korisnikhello(Model model) {
        return "korisnik_pocetna";
    }
    @GetMapping("/admin")
    public String adminhello(Model model) {
        return "admin_pocetna";
    }



}
