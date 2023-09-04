package tcc.uff.auth.server.model.document.secury;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@ToString()
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("logger")
@Document(collection = "loggers")
public class LoggerResponse {

    @MongoId(targetType = FieldType.OBJECT_ID)
    private String id;

    private String uri;

    private Object response;

}
