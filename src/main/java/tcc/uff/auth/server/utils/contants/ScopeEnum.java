package tcc.uff.auth.server.utils.contants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScopeEnum {

    public static final String SCOPE_OPEN_ID = "openid";
    public static final String SCOPE_PROFILE = "profile";
    public static final String SCOPE_USER_READ = "user.read";
    public static final String SCOPE_USER_WRITE = "user.write";

}
