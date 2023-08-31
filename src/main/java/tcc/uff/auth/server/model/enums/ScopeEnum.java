package tcc.uff.auth.server.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import tcc.uff.auth.server.utils.contants.Scopes;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ScopeEnum {

    PROFILE(OidcScopes.PROFILE, "Perfil de Autenticação"),
    USER_READ(Scopes.USER_READ, "Ler informações dos recursos"),
    USER_WRITE(Scopes.USER_WRITE, "Alterar informações dos recursos"),
    UNKNOWN(Scopes.UNKNOWN, "Escopo Desconhecido");

    private final String constant;
    private final String description;

    public static ScopeEnum fromConstante(@NonNull String value) {
        return Arrays.stream(values())
                .filter(scope -> scope.constant.equals(value.toLowerCase()))
                .findFirst()
                .orElse(ScopeEnum.UNKNOWN);
    }
}
