package travel.travelapplication.plan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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
}
