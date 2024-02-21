package travel.travelapplication.place;

import travel.travelapplication.plan.enums.Category;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToOne
    @JoinColumn(name = "city_county_district")
    private Long location;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Long tagId;

    @Column
    private Long cost;

    @Column
    private LocalDateTime availableTime;

    public Place(Long id, String name, Long location, Category category, Long tagId, Long cost,
                 LocalDateTime availableTime) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.category = category;
        this.tagId = tagId;
        this.cost = cost;
        this.availableTime = availableTime;
    }
}
