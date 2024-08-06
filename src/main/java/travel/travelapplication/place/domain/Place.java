package travel.travelapplication.place.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.constant.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "Place")
@Getter
@NoArgsConstructor
public class Place {

    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @DBRef
    private ProvCity provCity;

    @DBRef
    private CityCountyDistrict district;

    @DBRef
    private List<Tag> tags = new ArrayList<>();

    public Place(String name, Category category, ProvCity provCity, CityCountyDistrict district, List<Tag> tags) {
        this.name = name;
        this.category = category;
        this.provCity = provCity;
        this.district = district;
        this.tags = tags;
    }
}
