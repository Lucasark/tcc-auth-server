package tcc.uff.auth.server.model.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoverChangeForm {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @NotBlank
    private String confirmation;

    @Pattern(regexp = PASSWORD_REGEX, flags = Pattern.Flag.UNICODE_CASE, message = "Senha fraca! Deve conter no minimo 8 caracteres, sendo eles 1 letra maiuscula, 1 minuscula e 1 caracter especial")
    private String password;

    @NotBlank(message = "Confirmação de email deverá estar preenchido")
    private String passwordConfirmation;

}
