package tcc.uff.auth.server.service.interfaces;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendConfirmation(String to) throws MessagingException;
}
