package travel.travelapplication.plan;

import travel.travelapplication.plan.enums.TransportationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private TransportationType type;

    public Transportation(Long id, String name, TransportationType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
