package projekat.jobeasy.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class AutentifikacijaService {
    private final JavaMailSender mailSender;


    @Autowired
    public AutentifikacijaService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String verificationLink) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);

        // HTML šablon
        String htmlContent = """
                <html>
                       <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                       <div style="max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); overflow: hidden;">
                         <div style="padding: 20px; text-align: center; background-color: #1f63b0; color: white;">
                           <h2>Verifikacija naloga</h2>
                         </div>
                         <div style="padding: 20px; color: #333333;">
                           <p style="font-size: 16px;">Hvala vam što ste se registrovali na našoj platformi. Kliknite na dugme ispod kako biste verifikovali svoj nalog:</p>
                           <div style="text-align: center; margin: 20px 0;">
                             <a href="%s" style="background-color: #1f63b0; color: white; padding: 12px 20px; text-decoration: none; font-size: 16px; border-radius: 5px; display: inline-block; font-weight: bold;">Verifikuj nalog</a>
                           </div>
                           <p style="font-size: 14px; color: #777777;">Ako imate bilo kakvih problema, molimo vas da nas kontaktirate.</p>
                         </div>
                         <div style="padding: 15px; text-align: center; background-color: #1f63b0; color: white; font-size: 12px;">
                           <p>© 2025 JobEasy. Sva prava zadržana.</p>
                         </div>
                       </div>
                       </body>
                       </html>
        """;


        helper.setText(String.format(htmlContent, verificationLink), true);

        mailSender.send(mimeMessage);
    }


    public void sendInterviewEmail(String to, String subject, String candidateName, String date, String time, String location, String comment, boolean isForCompany) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);

        String messageBody;

        if (isForCompany) {
            messageBody = """
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                    <div style="max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); overflow: hidden;">
                        <div style="padding: 20px; text-align: center; background-color: #1f63b0; color: white;">
                            <h2>Potvrda zakazanog intervjua</h2>
                        </div>
                        <div style="padding: 20px; color: #333333;">
                            <p style="font-size: 16px;">Uspešno ste zakazali intervju sa kandidatom <strong>%s</strong>.</p>
                            <p><strong>Datum:</strong> %s</p>
                            <p><strong>Vrijeme:</strong> %s</p>
                            <p><strong>Lokacija:</strong> %s</p>
                            <p><strong>Komentar:</strong> %s</p>
                        </div>
                        <div style="padding: 15px; text-align: center; background-color: #1f63b0; color: white; font-size: 12px;">
                            <p>© 2025 JobEasy. Sva prava zadržana.</p>
                        </div>
                    </div>
                </body>
            </html>
        """;
        } else {
            messageBody = """
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                    <div style="max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); overflow: hidden;">
                        <div style="padding: 20px; text-align: center; background-color: #1f63b0; color: white;">
                            <h2>Zakazan intervju</h2>
                        </div>
                        <div style="padding: 20px; color: #333333;">
                            <p style="font-size: 16px;">Poštovani %s, vaš intervju je uspešno zakazan!</p>
                            <p><strong>Datum:</strong> %s</p>
                            <p><strong>Vrijeme:</strong> %s</p>
                            <p><strong>Lokacija:</strong> %s</p>
                            <p><strong>Komentar:</strong> %s</p>
                            <p style="font-size: 14px; color: #777777;">Srećno!</p>
                        </div>
                        <div style="padding: 15px; text-align: center; background-color: #1f63b0; color: white; font-size: 12px;">
                            <p>© 2025 JobEasy. Sva prava zadržana.</p>
                        </div>
                    </div>
                </body>
            </html>
        """;
        }

        helper.setText(String.format(messageBody, candidateName, date, time, location, comment), true);
        mailSender.send(mimeMessage);
    }

}
