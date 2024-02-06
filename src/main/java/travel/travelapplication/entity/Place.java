package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import travel.travelapplication.constant.Category;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Long cost;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate availableTime;

    @OneToOne
    @JoinColumn(name = "city_id")
    private CityCountyDistrict city; // location

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToMany
    @JoinTable(name = "PLACE_TAG",
        joinColumns = @JoinColumn(name = "PLACE_ID"),
        inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private List<Tag> tagList;

    @Builder
    public Place(String name, Category category, Long cost, LocalDate availableTime,
                 CityCountyDistrict city, Plan plan, List<Tag> tagList) {
        this.name = name;
        this.category = category;
        this.cost = cost;
        this.availableTime = availableTime;
        this.city = city;
        this.plan = plan;
        this.tagList = tagList;
    }
}
