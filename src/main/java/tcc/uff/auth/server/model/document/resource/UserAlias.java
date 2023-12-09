package tcc.uff.auth.server.model.document.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAlias {

    private String name;

    /**
     * Embed
     */
    private String courseId;

}