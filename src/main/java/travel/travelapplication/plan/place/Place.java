package travel.travelapplication.plan.place;

import travel.travelapplication.plan.enums.Category;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
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

}
