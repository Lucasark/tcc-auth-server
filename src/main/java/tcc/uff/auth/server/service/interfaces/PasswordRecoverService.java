package tcc.uff.auth.server.service.interfaces;

import tcc.uff.auth.server.model.document.user.UserDocument;
import tcc.uff.auth.server.model.web.PasswordRecoverForm;

public interface PasswordRecoverService {

    void registerByForm(PasswordRecoverForm form, UserDocument user);

    void changePassword(String password, String id);

}
