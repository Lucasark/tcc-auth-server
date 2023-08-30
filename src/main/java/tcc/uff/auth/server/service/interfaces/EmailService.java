package tcc.uff.auth.server.service.interfaces;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendToken(String to, String token) throws MessagingException;
}
