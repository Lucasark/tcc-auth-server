package tcc.uff.auth.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tcc.uff.auth.server.model.web.PasswordRecoverForm;
import tcc.uff.auth.server.model.web.TokenForm;
import tcc.uff.auth.server.repository.auth.UserRepository;
import tcc.uff.auth.server.service.interfaces.TokenService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/password-recover")
public class PasswordRecoverController {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @GetMapping
    public String getPasswordRecover(PasswordRecoverForm form) {
        return "password-recover";
    }

    @PostMapping
    public String registerSubmit(@ModelAttribute PasswordRecoverForm form,
                                 BindingResult result,
                                 Model model
    ) {

        if (result.hasErrors()) {
            return "register";
        }

        var user = userRepository.findByUsername(form.getEmail());


        model.addAttribute("email", form.getEmail());
        model.addAttribute("header", "Validação de e-mail");

        if (user.isEmpty()) {
            model.addAttribute("notFound", true);
            return "password-recover";
        }

        model.addAttribute("path", "password-recover");
        model.addAttribute("tokenForm", new TokenForm());
        return "token";
    }

    @PostMapping("/token")
    public String tokenValidation(@ModelAttribute @Valid TokenForm form,
                                  BindingResult result,
                                  Model model
    ) {

        var confirmation = tokenService.confirmTokenByFormPR(form);

        if (Boolean.TRUE.equals(confirmation.getSuccess())) {
            return "success-register";
        }

        model.addAttribute("header", "Validação de e-mail");
        model.addAttribute("validationException", confirmation.getErrorDescription());
        model.addAttribute("email", form.getEmail());
        return "token-register";

    }
}
