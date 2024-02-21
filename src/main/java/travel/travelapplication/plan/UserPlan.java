package travel.travelapplication.plan;

import travel.travelapplication.common.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class UserPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String myPlanName;

    @Column
    private LocalDateTime startAt;

    @Column
    private LocalDateTime endAt;

    @Column
    private Long budget;

    @Column
    private Long routeId;

    public UserPlan(Long id, String myPlanName, LocalDateTime startAt, LocalDateTime endAt, Long budget, Long routeId) {
        this.id = id;
        this.myPlanName = myPlanName;
        this.startAt = startAt;
        this.endAt = endAt;
        this.budget = budget;
        this.routeId = routeId;
    }
}
