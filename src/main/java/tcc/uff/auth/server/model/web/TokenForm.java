package tcc.uff.auth.server.model.web;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenForm {

    private String email;

    @Size(min = 5, message = "Token deve ser no minimo 5")
    private String token;
}
