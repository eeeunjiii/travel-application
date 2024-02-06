package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProvCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityCountyDistrict city;

    @Builder
    public ProvCity(String name, CityCountyDistrict city) {
        this.name = name;
        this.city = city;
    }
}
