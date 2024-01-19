package tcc.uff.auth.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationDTO {

    private String errorDescription;

    private String value;

    @Builder.Default
    private Boolean success = Boolean.FALSE;
}
