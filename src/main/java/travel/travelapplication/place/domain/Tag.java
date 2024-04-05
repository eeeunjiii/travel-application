package travel.travelapplication.place.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Tag")
@Getter
public class Tag {

    @Id
    private ObjectId id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
