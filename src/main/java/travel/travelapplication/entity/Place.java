package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.constant.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("Place")
@Getter
public class Place {

    @Id
    private ObjectId id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Long cost;

    @Temporal(TemporalType.TIME)
    private Date availableTime;

    @DBRef
    private CityCountyDistrict city; // location

    @DBRef
    private List<Tag> tags = new ArrayList<>();

    public Place(String name, Category category, Long cost, Date availableTime,
                 CityCountyDistrict city, List<Tag> tags) {
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.availableTime = availableTime;
        this.city = city;
        this.tags = tags;
    }
}
