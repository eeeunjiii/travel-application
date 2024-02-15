package travel.travelapplication.plan.place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
