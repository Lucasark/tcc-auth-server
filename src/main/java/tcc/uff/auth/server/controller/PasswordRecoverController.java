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
import org.springframework.web.servlet.ModelAndView;
import tcc.uff.auth.server.model.web.PasswordRecoverChangeForm;
import tcc.uff.auth.server.model.web.PasswordRecoverForm;
import tcc.uff.auth.server.model.web.TokenForm;
import tcc.uff.auth.server.repository.auth.UserRepository;
import tcc.uff.auth.server.service.interfaces.PasswordRecoverService;
import tcc.uff.auth.server.service.interfaces.TokenService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/password-recover")
public class PasswordRecoverController {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordRecoverService passwordRecoverService;

    @GetMapping
    public String getPasswordRecover(PasswordRecoverForm form) {
        return "password-recover";
    }

    @PostMapping
    public String registerSubmit(@ModelAttribute PasswordRecoverForm form,
                                 BindingResult result,
                                 Model model
    ) {

        var user = userRepository.findByUsername(form.getEmail());

        model.addAttribute("path", "password-recover");
        model.addAttribute("header", "Validação de e-mail");
        model.addAttribute("email", form.getEmail());

        if (user.isEmpty()) {
            model.addAttribute("notFound", true);
            return "password-recover";
        }

        model.addAttribute("path", "password-recover");
        model.addAttribute("tokenForm", new TokenForm());
        passwordRecoverService.registerByForm(form, user.get());
        return "token";
    }

    @PostMapping("/token")
    public Object tokenValidation(@ModelAttribute @Valid TokenForm form,
                                  BindingResult result,
                                  Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("header", "Validação de e-mail");
            model.addAttribute("email", form.getEmail());
            model.addAttribute("path", "password-recover");
            return "/token";
        }

        var confirmation = tokenService.confirmTokenByFormRecover(form);

        if (Boolean.TRUE.equals(confirmation.getSuccess())) {
            model.addAttribute("passwordRecoverChangeForm", new PasswordRecoverChangeForm());
            model.addAttribute("confirmation", confirmation.getValue());
            return "password-recover-change";
        }

        model.addAttribute("header", "Validação de e-mail");
        model.addAttribute("path", "password-recover");
        model.addAttribute("tokenForm", new TokenForm());
        model.addAttribute("validationException", confirmation.getErrorDescription());
        model.addAttribute("email", form.getEmail());
        return new ModelAndView("token", model.asMap());
    }

    @PostMapping("/change")
    public Object changePassword(@ModelAttribute @Valid PasswordRecoverChangeForm form,
                                 BindingResult result,
                                 Model model
    ) {
        model.addAttribute("confirmation", form.getConfirmation());

        if (result.hasErrors()) {
            return "password-recover-change";
        }

        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            model.addAttribute("passwordRecoverChangeForm", new PasswordRecoverChangeForm());
            model.addAttribute("confirmationPasswordError", "Senha não são iguais!");
            return "password-recover-change";
        }
        try {
            passwordRecoverService.changePassword(form.getPassword(), form.getConfirmation());
            return "success";
        } catch (Exception e) {
            model.addAttribute("passwordRecoverChangeForm", new PasswordRecoverChangeForm());
            model.addAttribute("confirmationPasswordError", e.getMessage());
            return "password-recover-change";
        }
    }
}
