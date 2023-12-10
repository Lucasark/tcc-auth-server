package tcc.uff.auth.server.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tcc.uff.auth.server.model.document.resource.UserResourceDocument;
import tcc.uff.auth.server.model.document.user.TokenValidation;
import tcc.uff.auth.server.model.document.user.UserDocument;
import tcc.uff.auth.server.model.dto.ConfirmationDTO;
import tcc.uff.auth.server.model.web.RegisterForm;
import tcc.uff.auth.server.model.web.TokenForm;
import tcc.uff.auth.server.repository.auth.UserRepository;
import tcc.uff.auth.server.repository.resource.ResourceRepository;
import tcc.uff.auth.server.service.interfaces.EmailService;
import tcc.uff.auth.server.service.interfaces.RegistrationService;
import tcc.uff.auth.server.utils.GenerateString;
import tcc.uff.auth.server.utils.exceptions.BusinessException;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final ResourceRepository resourceRepository;
    private static final Integer ATTEMPT = 3;

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Override
    public void registerByForm(RegisterForm form) {

        var now = LocalDateTime.now();

        var token = GenerateString.generateRandomString(5).toUpperCase();

        var user = UserDocument.builder()
                .username(form.getEmail().toLowerCase())
                .password(encoder.encode(form.getPassword()))
                .activation(TokenValidation.builder()
                        .value(token)
                        .expiryOn(now.plusMinutes(10))
                        .build())
                .build();

        userRepository.save(user);

        UserResourceDocument resource;

        var userOp = resourceRepository.findByEmail(form.getEmail());

        if (userOp.isEmpty()) {
            resource = UserResourceDocument.builder()
                    .email(form.getEmail())
                    .name(form.getUsername())
                    .build();
        } else {
            resource = userOp.get();
            resource.setName(form.getUsername());
        }

        resourceRepository.save(resource);

        try {
            emailService.sendToken(form.getEmail(), token);
        } catch (MessagingException e) {
            userRepository.delete(user);
            resourceRepository.delete(resource);
            throw new BusinessException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Não foi possivel enviar Email", e.getMessage());
        }
    }

    @Override
    public ConfirmationDTO confirmTokenByForm(TokenForm form) {

        var user = userRepository.findByUsername(form.getEmail());

        var now = LocalDateTime.now();

        String error = null;

        var token = GenerateString.generateRandomString(5);

        //Existe User
        if (user.isPresent()) {

            //Já está ativo
            if (Boolean.FALSE.equals(user.get().getActivation().getEnabled())) {

                //Limite de Tentativa
                if (user.get().getActivation().getAttempt() <= ATTEMPT) {

                    //Expirado
                    if (user.get().getActivation().getExpiryOn().isAfter(now)) {

                        //Token igual
                        if (!user.get().getActivation().getValue().equals(form.getToken())) {
                            error = "Token não é o mesmo";
                            user.get().getActivation().setAttempt(user.get().getActivation().getAttempt() + 1);
                        } else {
                            user.get().getActivation().setEnabled(Boolean.TRUE);
                            user.get().getActivation().setEnabledIn(now);
                        }

                    } else {
                        error = "Token Expirado";

                        user.get().setActivation(TokenValidation.builder()
                                .attempt(user.get().getActivation().getAttempt() + 1)
                                .value(token)
                                .build());
                    }
                } else {

                    var timeout = user.get().getActivation().getExpiryOn().plusMinutes(20);

                    if (timeout.isBefore(now)) {
                        error = "Novo token gerado!";

                        try {
                            emailService.sendToken(form.getEmail(), token);
                        } catch (MessagingException e) {
                            throw new BusinessException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Não foi possivel enviar Email", e.getMessage());
                        }

                        user.get().setActivation(TokenValidation.builder()
                                .expiryOn(now.plusMinutes(10))
                                .value(token)
                                .build());

                    } else {
                        error = "Muitas tentativas, aguarde alguns minutos!";

                        user.get().getActivation().setAttempt(user.get().getActivation().getAttempt() + 1);
                    }
                }

                userRepository.save(user.get());
                return ConfirmationDTO.builder().success(isNull(error) ? Boolean.TRUE : Boolean.FALSE).errorDescription(error).build();
            } else {
                return ConfirmationDTO.builder().success(Boolean.TRUE).build();
            }

        } else {
            return ConfirmationDTO.builder().errorDescription("usuario não encontrato").build();
        }
    }
}