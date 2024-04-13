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
                "<style>" +
                        "    * {" +
                        "        font-family: Arial, sans-serif;" +
                        "    }" +
                        "    img {" +
                        "       width: 110px;" +
                        "       height: 75px;" +
                        "       margin-bottom: 8px;" +
                        "    }" +
                        "</style>" +
                        "<div>" +
                        "    <img src=\"https://authorization-server-d6ca554d3cbd.herokuapp.com/v1/auth/assets/images/logoBlue.png\" alt=\"Aluno Presente!\" width=\"110px\" height=\"75px\">" +
                        "    <p>Bem-vindo ao <b>Aluno Presente!</b></p>" +
                        "    <p>Para finalizar o processo, por favor, insira o código gerado:</p>" +
                        "    <h3>%s</h3>" +
                        "    <p>Se você não solicitou este código, por favor, ignore este e-mail.</p>" +
                        "</div>",
                token
        );
        message.setContent(htmlContent, "text/html; charset=utf-8");

        javaMailSender.send(message);

    }
}
