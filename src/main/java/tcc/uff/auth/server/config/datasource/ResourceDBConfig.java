package tcc.uff.auth.server.config.datasource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"tcc.uff.auth.server.repository.resource"},
        mongoTemplateRef = ResourceDBConfig.MONGO_TEMPLATE
)
@NoArgsConstructor(access = AccessLevel.NONE)
public class ResourceDBConfig {
    protected static final String MONGO_TEMPLATE = "resourceMongoTemplate";

}
