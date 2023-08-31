package tcc.uff.auth.server.utils.contants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
/**
 * Obrigatorio ser em LowCase
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Scopes {

    public static final String USER_READ = "user.read";
    public static final String USER_WRITE = "user.write";
    public static final String UNKNOWN = "UNKNOWN";

}
