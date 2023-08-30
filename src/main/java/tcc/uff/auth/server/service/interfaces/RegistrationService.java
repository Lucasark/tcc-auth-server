package tcc.uff.auth.server.service.interfaces;

import tcc.uff.auth.server.model.dto.ConfirmationDTO;
import tcc.uff.auth.server.model.web.RegisterForm;
import tcc.uff.auth.server.model.web.TokenForm;

public interface RegistrationService {

    void registerByForm(RegisterForm form);

    ConfirmationDTO confirmTokenByForm(TokenForm form);
}
