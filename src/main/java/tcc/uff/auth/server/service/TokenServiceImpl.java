package tcc.uff.auth.server.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tcc.uff.auth.server.model.document.user.TokenValidation;
import tcc.uff.auth.server.model.dto.ConfirmationDTO;
import tcc.uff.auth.server.model.web.TokenForm;
import tcc.uff.auth.server.repository.auth.UserRepository;
import tcc.uff.auth.server.service.interfaces.EmailService;
import tcc.uff.auth.server.service.interfaces.TokenService;
import tcc.uff.auth.server.utils.GenerateString;
import tcc.uff.auth.server.utils.exceptions.BusinessException;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Integer ATTEMPT = 5;

    private final EmailService emailService;
    private final UserRepository userRepository;

    @Override
    public ConfirmationDTO confirmTokenByForm(TokenForm form) {

        var user = userRepository.findByUsername(form.getEmail());

        //Existe User
        if (user.isPresent()) {

            //Já está ativo
            if (Boolean.FALSE.equals(user.get().getActivation().getEnabled())) {

                var error = fluxToken(user.get().getActivation(), form.getToken(), form.getEmail());

                userRepository.save(user.get());
                return ConfirmationDTO.builder().success(isNull(error) ? Boolean.TRUE : Boolean.FALSE).errorDescription(error).build();
            } else {
                return ConfirmationDTO.builder().success(Boolean.TRUE).build();
            }

        } else {
            return ConfirmationDTO.builder().errorDescription("Conta não encontrada!").build();
        }
    }

    @Override
    public ConfirmationDTO confirmTokenByFormPR(TokenForm form) {
        var user = userRepository.findByUsername(form.getEmail());

        //Existe User
        if (user.isPresent()) {

            var error = fluxToken(user.get().getRecover(), form.getToken(), form.getEmail());

            userRepository.save(user.get());

            return ConfirmationDTO.builder().success(isNull(error) ? Boolean.TRUE : Boolean.FALSE).errorDescription(error).build();

        } else {
            return ConfirmationDTO.builder().errorDescription("Conta não encontrada!").build();
        }
    }

    private String fluxToken(TokenValidation tokenValidation, String token, String email) {

        var now = LocalDateTime.now();

        var tokenNew = GenerateString.generateRandomString(5);

        //Limite de Tentativa
        if (tokenValidation.getAttempt() <= ATTEMPT) {

            tokenValidation.setLastAttempt(now);

            //Expirado
            if (tokenValidation.getExpiryOn().isAfter(now)) {

                //Token igual
                if (!tokenValidation.getValue().equals(token)) {
                    tokenValidation.setAttempt(tokenValidation.getAttempt() + 1);
                    return "Token não é o mesmo";
                } else {
                    tokenValidation.setEnabled(Boolean.TRUE);
                    tokenValidation.setEnabledIn(now);
                    return null;
                }

            } else {
                tokenValidation.setAttempt(tokenValidation.getAttempt() + 1);
                tokenValidation.setExpiryOn(now.plusMinutes(10));
                tokenValidation.setValue(tokenNew);

                sendToken(email, tokenNew);
                return "Token Expirado, gerando um novo!";
            }
        } else {

            if (tokenValidation.getLastAttempt().plusMinutes(30).isBefore(now)) {
                tokenValidation.setLastAttempt(now);
                tokenValidation.setExpiryOn(now.plusMinutes(10));
                tokenValidation.setValue(tokenNew);
                tokenValidation.setAttempt(0L);

                sendToken(email, tokenNew);
                return "Novo token gerado!";
            } else {
                return "Muitas tentativas, aguarde alguns minutos!";
            }
        }
    }

    private void sendToken(String email, String token) {
        try {
            emailService.sendToken(email, token);
        } catch (MessagingException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Não foi possivel enviar Email", e.getMessage());
        }
    }

    @Override
    public void resendToken(String email) {

        var user = userRepository.findByUsername(email);

        if (user.isPresent()) {

            fluxResend(user.get().getActivation(), user.get().getActivation().getValue(), email);

            userRepository.save(user.get());
        }
    }

    private void fluxResend(TokenValidation tokenValidation, String token, String email) {
        var now = LocalDateTime.now();

        var tokenNew = GenerateString.generateRandomString(5);

        //Limite de Tentativa
        if (tokenValidation.getAttempt() <= ATTEMPT) {

            tokenValidation.setValue(token);
            tokenValidation.setLastAttempt(now);
            tokenValidation.setExpiryOn(now.plusMinutes(10));
            tokenValidation.setAttempt(tokenValidation.getAttempt() + 1);

            sendToken(email, tokenNew);
        } else {
            log.warn("Muitas tentativas de envio de token");
        }
    }
}