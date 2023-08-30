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
import tcc.uff.auth.server.model.web.RegisterForm;
import tcc.uff.auth.server.model.web.TokenForm;
import tcc.uff.auth.server.repository.auth.UserRepository;
import tcc.uff.auth.server.service.interfaces.RegistrationService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UserRepository userRepository;
    private final RegistrationService registrationService;

    @GetMapping
    public String register(RegisterForm form) {
        return "register";
    }

    @PostMapping
    public String registerSubmit(@ModelAttribute @Valid RegisterForm form,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            model.addAttribute("confirmationPasswordMatch", "Email não são iguais!");
            return "register";
        }

        var user = userRepository.findByUsername(form.getEmail());

        if (user.isPresent()) {

            if (Boolean.TRUE.equals(user.get().getActivation().getEnabled())) {
                model.addAttribute("duplicatedEmail", "Email já está cadastrado!");
                return "register";
            } else {
                model.addAttribute("tokenForm", new TokenForm());
                model.addAttribute("email", form.getEmail());
                model.addAttribute("notActive", true);
                return "token-register";
            }
        }

        registrationService.registerByForm(form);

        model.addAttribute("tokenForm", new TokenForm());
        model.addAttribute("email", form.getEmail());
        return "token-register";
    }

    @PostMapping("/token")
    public String tokenValidation(@ModelAttribute @Valid TokenForm form,
                                  BindingResult result,
                                  Model model) {

        var confirmation = registrationService.confirmTokenByForm(form);

        if (Boolean.TRUE.equals(confirmation.getSuccess())) {
            return "success-register";
        }

        model.addAttribute("validationException", confirmation.getErrorDescription());
        model.addAttribute("email", form.getEmail());
        return "token-register";

    }
}
