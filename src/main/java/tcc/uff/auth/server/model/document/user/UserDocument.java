package tcc.uff.auth.server.model.document.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Para o Lucas do Futuro:
 * <p>
 * A implementação do UserDetails conflita com a regra de Deserialzação de OAuth, seria necessario aplicar uma deserilacação personalizada
 * para o UserDetails (<a href="https://github.com/spring-projects/spring-session/issues/783">implementação</a>)
 * Além disso, iria violar a regra de classe do authrization entity que deveria salvar um "simples" UserDetails e não TUDO.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("User")
@ToString(callSuper = true)
@Document(collection = "users")
public class UserDocument {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String username;

    @NonNull
    private String password;

    @Builder.Default
    private Set<String> authorities = new HashSet<>();

    @Builder.Default
    private Boolean accountNonExpired = Boolean.TRUE;

    @Builder.Default
    private Boolean accountNonLocked = Boolean.TRUE;

    @Builder.Default
    private Boolean credentialsNonExpired = Boolean.TRUE;

    private TokenValidation activation;

    private TokenValidation recover;
}
