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
public class ProvCity {

    @Id
    private Long id;

    private String name;

    @DBRef
    private List<CityCountyDistrict> citys = new ArrayList<>();

    @Builder
    public ProvCity(String name, List<CityCountyDistrict> citys) {
        this.name = name;
        this.citys = citys;
    }
}
