package projekat.jobeasy.Controllers;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.*;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/cv")
public class CvController {

    private static final String PDF_DIRECTORY = System.getProperty("user.dir") + "/cvprijave/";

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getCvFile(@PathVariable String fileName) throws IOException {
        Path pdfPath = Paths.get(PDF_DIRECTORY + fileName);

        if (!Files.exists(pdfPath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] pdfBytes = Files.readAllBytes(pdfPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
