package tcc.uff.auth.server.repository.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tcc.uff.auth.server.model.document.secury.AuthorizationConsentDocument;

import java.util.Optional;

@Repository
public interface AuthorizationConsentRepository extends MongoRepository<AuthorizationConsentDocument, AuthorizationConsentDocument.AuthorizationConsentId> {

    Optional<AuthorizationConsentDocument> findByIdRegisteredClientIdAndIdPrincipalName(String registeredClientId, String principalName);

    void deleteByIdRegisteredClientIdAndIdPrincipalName(String registeredClientId, String principalName);

}
