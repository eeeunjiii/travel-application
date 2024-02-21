package travel.travelapplication.place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class CityCountyDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "prov_city")
    private Long provId;

    public CityCountyDistrict(Long id, String name, Long provId) {
        this.id = id;
        this.name = name;
        this.provId = provId;
    }
}
