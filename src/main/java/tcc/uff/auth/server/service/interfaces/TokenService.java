package tcc.uff.auth.server.service.interfaces;

import tcc.uff.auth.server.model.dto.ConfirmationDTO;
import tcc.uff.auth.server.model.web.TokenForm;

public interface TokenService {

    ConfirmationDTO confirmTokenByForm(TokenForm form);

    ConfirmationDTO confirmTokenByFormRecover(TokenForm form);

    void resendToken(String email);
}
