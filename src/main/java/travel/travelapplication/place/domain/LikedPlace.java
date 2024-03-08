package travel.travelapplication.place.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("LikedPlace")
@Getter
public class LikedPlace {

    @Id
    private ObjectId id;

    @DBRef
    private Place place;

    public LikedPlace(Place place) {
        this.place = place;
    }
}