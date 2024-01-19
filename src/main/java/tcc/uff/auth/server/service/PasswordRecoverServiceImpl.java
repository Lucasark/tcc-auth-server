package tcc.uff.auth.server.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tcc.uff.auth.server.model.document.user.TokenValidation;
import tcc.uff.auth.server.model.document.user.UserDocument;
import tcc.uff.auth.server.model.web.PasswordRecoverForm;
import tcc.uff.auth.server.repository.auth.UserRepository;
import tcc.uff.auth.server.service.interfaces.EmailService;
import tcc.uff.auth.server.service.interfaces.PasswordRecoverService;
import tcc.uff.auth.server.utils.GenerateString;
import tcc.uff.auth.server.utils.exceptions.BusinessException;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordRecoverServiceImpl implements PasswordRecoverService {

    private static final String SUBJECT = "Token para validar!";

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void registerByForm(PasswordRecoverForm form, UserDocument user) {

        var now = LocalDateTime.now();

        var token = GenerateString.generateRandomString(5).toUpperCase();

        if (nonNull(user.getRecover())) {

            var recover = user.getRecover();

            if (Boolean.TRUE.equals(recover.getEnabled())) {
                setNewToken(user, now, token);
            } else {
                //TODO: Logica temporal e tentativas de token
                setNewToken(user, now, token);
            }

        } else {
            setNewToken(user, now, token);
        }

        userRepository.save(user);

        try {
            emailService.sendToken(form.getEmail(), token, SUBJECT);
        } catch (MessagingException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Não foi possivel enviar Email", e.getMessage());
        }
    }

    @Override
    public void changePassword(String password, String id) throws RuntimeException {
        var userFound = userRepository.findByRecoverId(new ObjectId(id));

        if (userFound.isPresent()) {
            var user = userFound.get();

            if (Boolean.TRUE.equals(user.getRecover().getEnabled())) {
                user.setPassword(encoder.encode(password));
                user.setRecover(null);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Token não habilitado!");
            }
        } else {
            throw new RuntimeException("Usario sem solicitação de troca de senha!");
        }
    }

    private void setNewToken(UserDocument user, LocalDateTime now, String token) {
        user.setRecover(TokenValidation.builder()
                .value(token)
                .expiryOn(now.plusMinutes(5))
                .build());
    }
}