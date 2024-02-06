package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import travel.travelapplication.constant.Status;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate endDate;
    private Long budget;

    @Enumerated(EnumType.STRING)
    private Status status;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createdAt;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userPlan")
    private List<Route> routeList;

    @Builder
    public UserPlan(String name, LocalDate startDate, LocalDate endDate, Long budget,
                    Status status, LocalDate createdAt, LocalDate updatedAt, User user) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    public void addRoute(Route route) {
//        this.routeList.add(route);
        routeList.add(route);
    }

    public void updatePlanName(String name) {
        this.name=name;
    }
}
