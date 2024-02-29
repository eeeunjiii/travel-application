package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@RequiredArgsConstructor
@ToString
@Getter
public class LikedPlace {

    @Id
    private Long id;

    @DBRef
    private Place place;

    public LikedPlace(Place place) {
        this.place = place;
    }
}