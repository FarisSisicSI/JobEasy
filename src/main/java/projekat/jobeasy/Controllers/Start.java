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

    @GetMapping("/pocetna")
    public String hello(Model model) {
        return "welcome";
    }




}
