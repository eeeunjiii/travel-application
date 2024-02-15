package travel.travelapplication.user;

import travel.travelapplication.common.BaseEntity;
import travel.travelapplication.plan.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "my_plan")
    private Long myPlanId;

    @ManyToOne
    @JoinColumn(name = "saved_plan")
    private Long savedPlanId;

    @ManyToOne
    @JoinColumn(name = "liked_place")
    private Long likedPlaceId;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

}
