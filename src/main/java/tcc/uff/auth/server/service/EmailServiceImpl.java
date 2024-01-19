package tcc.uff.auth.server.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tcc.uff.auth.server.service.interfaces.EmailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendToken(String to, String token, String subject) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setRecipients(Message.RecipientType.TO, to);
        message.setSubject(subject);

        String htmlContent = String.format(
                " " +
                        "<h1> Token de Confirmação </h1>" +
                        "<p>Token: <strong> %s </strong> !</p>",
                token
        );
        message.setContent(htmlContent, "text/html; charset=utf-8");

        javaMailSender.send(message);

    }
}
