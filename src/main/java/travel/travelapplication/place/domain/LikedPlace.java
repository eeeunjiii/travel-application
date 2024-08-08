package travel.travelapplication.place.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LikedPlace")
@Getter
public class LikedPlace {

    @Id
    private ObjectId id;

    @DBRef
    private Place likedPlace;

    @PersistenceCreator
    public LikedPlace() {
    }

    @PersistenceCreator
    public LikedPlace(Place likedPlace) {
        this.likedPlace = likedPlace;
    }
}