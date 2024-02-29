package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@RequiredArgsConstructor
@ToString
@Getter
public class Route {

    @Id
    private Long id;

    @DBRef
    private List<Place> places = new ArrayList<>();

    @DBRef
    private List<Transportation> transportations = new ArrayList<>();

    public Route(List<Place> places, List<Transportation> transportations) {
        this.places = places;
        this.transportations = transportations;
    }
}
