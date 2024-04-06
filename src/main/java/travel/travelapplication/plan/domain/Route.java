package travel.travelapplication.plan.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.place.domain.Place;

import java.util.ArrayList;
import java.util.List;

@Document("Route")
@Getter
public class Route {

    @Id
    private ObjectId id;

    @DBRef
    private List<Place> places = new ArrayList<>();

    @DBRef
    private List<Transportation> transportations = new ArrayList<>();

    public Route(List<Place> places, List<Transportation> transportations) {
        this.places = places;
        this.transportations = transportations;
    }
}
