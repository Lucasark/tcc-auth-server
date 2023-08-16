package tcc.uff.auth.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static tcc.uff.auth.server.config.ContantsConfig.BACK_PATH_LOGIN;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(HttpServletRequest request) {
        DefaultSavedRequest a = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        var b = a.getRedirectUrl();
        if (b.contains("/oauth2/authorize")) {
            request.getSession().setAttribute(BACK_PATH_LOGIN, a);
        }
        return "login";
    }


}
