package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.constant.TransportationType;

@Document("Transportation")
@Getter
public class Transportation {

    @Id
    private ObjectId id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TransportationType type;

    public Transportation(String name, TransportationType type) {
        this.name = name;
        this.type = type;
    }
}
