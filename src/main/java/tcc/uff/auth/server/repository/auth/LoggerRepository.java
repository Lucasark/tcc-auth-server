package tcc.uff.auth.server.repository.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tcc.uff.auth.server.model.document.secury.LoggerResponse;

@Repository
public interface LoggerRepository extends MongoRepository<LoggerResponse, String> {

}
