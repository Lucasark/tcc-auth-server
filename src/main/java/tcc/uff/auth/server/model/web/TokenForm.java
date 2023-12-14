package tcc.uff.auth.server.model.web;

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
public class TokenForm {

    @Getter(AccessLevel.NONE)
    private String email;

    @Size(min = 5, message = "Token deve ser no minimo 5")
    private String token;

    public String getEmail() {
        if(Optional.ofNullable(email).isPresent()){
            return email.toLowerCase();
        }
        return null;
    }
}
