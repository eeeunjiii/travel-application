package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@RequiredArgsConstructor
@ToString
@Getter
public class CityCountyDistrict {

    @Id
    private Long id;

    private String name;

    public CityCountyDistrict(String name) {
        this.name = name;
    }
}
