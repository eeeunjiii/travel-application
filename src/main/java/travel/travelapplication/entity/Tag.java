package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tagList")
    private List<Place> placeList;

    @Builder
    public Tag(String name) {
        this.name = name;
    }

    public void addPlace(Place place){
//        this.placeList.add(place);
        placeList.add(place);
    }
}
