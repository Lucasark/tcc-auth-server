package tcc.uff.auth.server.repository.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tcc.uff.auth.server.model.document.secury.AuthorizationDocument;

import java.util.Optional;

@Repository
public interface AuthorizationRepository extends MongoRepository<AuthorizationDocument, String> {
    Optional<AuthorizationDocument> findByState(String state);

    Optional<AuthorizationDocument> findByAuthorizationCodeValue(String authorizationCode);

    Optional<AuthorizationDocument> findByAccessTokenValue(String accessToken);

    Optional<AuthorizationDocument> findByRefreshTokenValue(String refreshToken);

    Optional<AuthorizationDocument> findByOidcIdTokenValue(String idToken);

    Optional<AuthorizationDocument> findByUserCodeValue(String userCode);

    Optional<AuthorizationDocument> findByDeviceCodeValue(String deviceCode);

    //TODO: FAZER A QUERY
//    @Query("select a from Authorization a where a.state = :token" +
//            " or a.authorizationCodeValue = :token" +
//            " or a.accessTokenValue = :token" +
//            " or a.refreshTokenValue = :token" +
//            " or a.oidcIdTokenValue = :token" +
//            " or a.userCodeValue = :token" +
//            " or a.deviceCodeValue = :token"
//    )
//    Optional<AuthorizationDocument> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(@Param("token") String token);

    @Query("{$or: [ " +
            "{ 'state' : ?0 }, " +
            "{ 'authorizationCodeValue' : ?0 }, " +
            "{ 'accessTokenValue' : ?0 }, " +
            "{ 'refreshTokenValue' : ?0 } " +
            "]}"
    )
    Optional<AuthorizationDocument> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(String token);
}
