package travel.travelapplication.plan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Long placeId;

    @ManyToOne
    @JoinColumn(name = "transportation_id")
    private Long transportationId;

    public Route(Long id, Long placeId, Long transportationId) {
        this.id = id;
        this.placeId = placeId;
        this.transportationId = transportationId;
    }
}
