package tcc.uff.auth.server.model.document.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationDocument implements Serializable {

    private String token;

    private LocalDateTime expiryOn;

    private LocalDateTime enabledIn;

    @Builder.Default
    private Boolean enabled = Boolean.FALSE;
}
