package tcc.uff.auth.server.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tcc.uff.auth.server.model.document.user.UserDocument;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, ObjectId> {

    Optional<UserDocument> findByUsername(String username);
}
