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
import tcc.uff.auth.server.model.web.RegisterForm;
import tcc.uff.auth.server.repository.auth.UserRepository;
import tcc.uff.auth.server.repository.resource.ResourceRepository;
import tcc.uff.auth.server.service.interfaces.EmailService;
import tcc.uff.auth.server.service.interfaces.RegistrationService;
import tcc.uff.auth.server.utils.GenerateString;
import tcc.uff.auth.server.utils.exceptions.BusinessException;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private static final String SUBJECT = "Concluir cadastro no Aluno Presente!";

    private final ResourceRepository resourceRepository;
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
            emailService.sendToken(form.getEmail(), token, SUBJECT);
        } catch (MessagingException e) {
            userRepository.delete(user);
            resourceRepository.delete(resource);
            throw new BusinessException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Não foi possivel enviar Email", e.getMessage());
        }
    }
}