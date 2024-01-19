package tcc.uff.auth.server.service.interfaces;

import tcc.uff.auth.server.model.dto.ConfirmationDTO;
import tcc.uff.auth.server.model.web.TokenForm;

public interface TokenService {

    ConfirmationDTO confirmTokenByForm(TokenForm form);

    //TODO: Juntar com o de cima!
    ConfirmationDTO confirmTokenByFormPR(TokenForm form);

    void resendToken(String email);
}
