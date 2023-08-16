package tcc.uff.auth.server.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

    private String username;
    private String email;
    private String password;
    private String passwordConfirmation;
}
