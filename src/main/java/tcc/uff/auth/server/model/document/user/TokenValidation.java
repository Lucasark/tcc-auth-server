package tcc.uff.auth.server.model.document.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidation implements Serializable {

    @Builder.Default
    private ObjectId id = new ObjectId();

    private String value;

    @Builder.Default
    private LocalDateTime lastAttempt = LocalDateTime.now();

    private LocalDateTime expiryOn;

    private LocalDateTime enabledIn;

    @Builder.Default
    private Long attempt = 0L;

    @Builder.Default
    private Boolean enabled = Boolean.FALSE;
}
