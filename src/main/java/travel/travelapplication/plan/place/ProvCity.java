package travel.travelapplication.plan.place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class ProvCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public ProvCity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
