package tcc.uff.auth.server.config.datasource;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
public class MultipleMongoConfig {
    @Primary
    @Bean(name = "authProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.auth")
    public MongoProperties getAuthProps() {
        return new MongoProperties();
    }

    @Bean(name = "resourceProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.resource")
    public MongoProperties getResourceProps() {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "authMongoTemplate")
    public MongoTemplate authMongoTemplate(MappingMongoConverter converter) {
        return new MongoTemplate(authMongoDatabaseFactory(getAuthProps()), converter);
    }

    @Bean(name = "resourceMongoTemplate")
    public MongoTemplate resourceMongoTemplate(MappingMongoConverter converter) {
        return new MongoTemplate(resourceMongoDatabaseFactory(getResourceProps()));
    }

    @Primary
    @Bean
    public MongoDatabaseFactory authMongoDatabaseFactory(MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

    @Bean
    public MongoDatabaseFactory resourceMongoDatabaseFactory(MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

}
