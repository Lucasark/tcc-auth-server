package tcc.uff.auth.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tcc.uff.auth.server.model.web.RegisterForm;
import tcc.uff.auth.server.repository.AuthorizationConsentRepository;
import tcc.uff.auth.server.repository.AuthorizationRepository;
import tcc.uff.auth.server.repository.ClientRepository;
import tcc.uff.auth.server.repository.UserRepository;
import tcc.uff.auth.server.service.interfaces.EmailService;

import static tcc.uff.auth.server.config.ContantsConfig.BACK_PATH_LOGIN;

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
    public String register() {
        return "register";
    }

    @PostMapping
    public String registerSubmit(@ModelAttribute RegisterForm form) {
        return "sucess-register";
    }

    @GetMapping(path = "/back")
    public String back(HttpServletRequest request) {
        var a = (DefaultSavedRequest) request.getSession().getAttribute(BACK_PATH_LOGIN);
        return "redirect:" + a.getRedirectUrl();
    }
}
