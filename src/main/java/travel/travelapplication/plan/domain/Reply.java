package travel.travelapplication.plan.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Document(collection = "ReplyComment")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    private String content;
    private String email;

    @CreatedDate
    private Date createdAt;

    @PersistenceCreator
    public Reply() {

    }

    @PersistenceCreator
    @Builder
    public Reply(ObjectId id, String content, String email, Date createdAt) {
        this.id = id;
        this.content = content;
        this.email = email;
        this.createdAt = createdAt;
    }
}
