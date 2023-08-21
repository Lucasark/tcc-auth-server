package tcc.uff.auth.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tcc.uff.auth.server.model.web.RegisterForm;
import tcc.uff.auth.server.repository.auth.AuthorizationConsentRepository;
import tcc.uff.auth.server.repository.auth.AuthorizationRepository;
import tcc.uff.auth.server.repository.auth.ClientRepository;
import tcc.uff.auth.server.repository.auth.UserRepository;
import tcc.uff.auth.server.service.interfaces.EmailService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final EmailService emailService;
    private final AuthorizationRepository authorizationRepository;
    private final UserRepository userRepository;
    private final AuthorizationConsentRepository authorizationConsentRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    @GetMapping
    public String register(RegisterForm form) {
        return "register";
    }

    @PostMapping
    public String registerSubmit(@ModelAttribute @Valid RegisterForm form,
                                 BindingResult result,
                                 Model model) {

        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            model.addAttribute("confirmationPasswordMatch", "Email não são iguais!");
        }

        if (result.hasErrors()) {
            return "register";
        }

        return "sucess-register";
    }

}
