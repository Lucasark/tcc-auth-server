package tcc.uff.auth.server.model.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoverForm {

    @Getter(AccessLevel.NONE)
    @NotBlank(message = "Email deverá estar preenchido")
    @Email(message = "Email inválido")
    private String email;

    public String getEmail() {
        if (Optional.ofNullable(email).isPresent()) {
            return email.toLowerCase();
        }
        return null;
    }
}
