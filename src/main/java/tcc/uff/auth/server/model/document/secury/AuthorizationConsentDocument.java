package tcc.uff.auth.server.model.document.secury;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TypeAlias("AuthorizationConsent")
@Document(collection = "authorizationConsents")
public class AuthorizationConsentDocument implements Serializable {

    @Id
    private AuthorizationConsentId id;

    private String authorities;

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorizationConsentId implements Serializable {
        private String registeredClientId;

        private String principalName;

    }

}

