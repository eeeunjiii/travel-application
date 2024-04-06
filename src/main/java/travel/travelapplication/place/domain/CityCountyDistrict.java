package travel.travelapplication.place.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("CityCountyDistrict")
@Getter
public class CityCountyDistrict {

    @Id
    private ObjectId id;

    private String name;

    public CityCountyDistrict(String name) {
        this.name = name;
    }
}
