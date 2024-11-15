package projekat.jobeasy.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Start {

    @GetMapping("/") // Handle the root URL
    public String home() {
        return "welcome"; // Return the home view
    }

    @GetMapping("/welcome")
    public String hello(Model model) {
        return "welcome";  // This maps to the "welcome.html" view
    }


}
