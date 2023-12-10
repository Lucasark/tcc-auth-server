package tcc.uff.auth.server.repository.resource;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tcc.uff.auth.server.model.document.resource.UserResourceDocument;

import java.util.Optional;

@Repository
public interface ResourceRepository extends MongoRepository<UserResourceDocument, String> {

    Optional<UserResourceDocument> findByName(String username);
    Optional<UserResourceDocument> findByEmail(String username);
}
