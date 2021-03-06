package ro.gini.iordache.email.sender.impl;


import com.gini.iordache.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ro.gini.iordache.email.sender.EmailHtmlRenderer;
import ro.gini.iordache.email.sender.EmailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {


    private final JavaMailSenderImpl mailSender;
    private final EmailHtmlRenderer emailHtmlRenderer;



    @Override
    public void sendEmail(User user){

        var userEmail = user.getEmail();
        var username = user.getUsername();
        var token = user.getActivationToken().getToken();



        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "utf-8");

        String link = "http://serviceauto3-env.eba-8ppwvwkb.eu-central-1.elasticbeanstalk.com/activate?token=" + token + "&email=" + userEmail;
        String email = emailHtmlRenderer.constructHtmlMailPage(link, username);


        try {
            helper.setSubject("Confirmation Token");
            helper.setTo(userEmail);
            helper.setFrom("application@service.com");
            helper.setText(email, true);
            mailSender.send(mailMessage);


        } catch ( MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send mail :(");
        }

    }
}
