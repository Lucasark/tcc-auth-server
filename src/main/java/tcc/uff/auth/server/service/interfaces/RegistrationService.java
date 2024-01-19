package tcc.uff.auth.server.service.interfaces;

import tcc.uff.auth.server.model.web.RegisterForm;

public interface RegistrationService {

    void registerByForm(RegisterForm form);

}
