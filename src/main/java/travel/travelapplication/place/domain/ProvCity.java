package travel.travelapplication.place.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("ProvCity")
@Getter
public class ProvCity {

    @Id
    private ObjectId id;

    private String name;

    @DBRef
    private List<CityCountyDistrict> citys = new ArrayList<>();

    public ProvCity(String name, List<CityCountyDistrict> citys) {
        this.name = name;
        this.citys = citys;
    }
}
