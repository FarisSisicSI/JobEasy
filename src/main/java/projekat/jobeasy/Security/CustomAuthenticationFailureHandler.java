package projekat.jobeasy.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage;

        if (exception instanceof DisabledException) {
            errorMessage = "Korisnički nalog nije verifikovan. Proverite svoj email.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Neispravno korisničko ime ili lozinka.";
        } else {
            errorMessage = "Došlo je do greške prilikom prijave. Pokušajte ponovo.";
        }

        // Encode the error message to pass it as a URL parameter
        String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

        // Redirect to the login page with the error message
        response.sendRedirect("/login?error=" + encodedErrorMessage);
    }
}
