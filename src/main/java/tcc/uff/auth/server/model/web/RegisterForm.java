package tcc.uff.auth.server.model.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    @Size(min = 2, message = "Nome deverá ter no minimo 2 letras")
    private String username;

    @Getter(AccessLevel.NONE)
    @NotBlank(message = "Email deverá estar preenchido")
    @Email(message = "Email inválido")
    private String email;

    @Pattern(regexp = PASSWORD_REGEX, flags = Pattern.Flag.UNICODE_CASE, message = "Senha fraca! Deve conter no minimo 8 caracteres, sendo eles 1 letra maiuscula, 1 minuscula e 1 caracter especial")
    private String password;

    @NotBlank(message = "Confirmação de email deverá estar preenchido")
    private String passwordConfirmation;

    public String getEmail() {
        if (Optional.ofNullable(email).isPresent()) {
            return email.toLowerCase();
        }
        return null;
    }
}
