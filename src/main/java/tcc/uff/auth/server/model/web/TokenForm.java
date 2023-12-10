package tcc.uff.auth.server.model.web;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenForm {

    @Getter(AccessLevel.NONE)
    private String email;

    @Size(min = 5, message = "Token deve ser no minimo 5")
    private String token;

    public String getEmail() {
        return email.toLowerCase();
    }
}
