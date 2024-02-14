package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan { // 커뮤니티에 올라오는 public 처리된 userplan

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String update;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createdAt;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate updatedAt;

    @OneToOne
    @JoinColumn(name = "userPlan_id")
    private UserPlan userPlan;

    @Builder
    public Plan(String name, String update, LocalDate createdAt, LocalDate updatedAt, UserPlan userPlan) {
        this.name = name;
        this.update = update;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userPlan = userPlan;
    }
}
