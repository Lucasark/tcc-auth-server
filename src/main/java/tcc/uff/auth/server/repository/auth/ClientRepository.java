package tcc.uff.auth.server.repository.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tcc.uff.auth.server.model.document.secury.ClientDocument;

import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<ClientDocument, String> {
    Optional<ClientDocument> findByClientId(String clientId);
}
