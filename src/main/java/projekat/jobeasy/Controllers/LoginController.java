package projekat.jobeasy.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {
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
    public String welcome() {
        // Return the welcome.html view
        return "welcome";
    }

}
