package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.ListableBeanFactoryExtensionsKt;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createdAt;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "user")
    private List<SavedPlan> savedPlanList;

    @OneToMany(mappedBy = "user")
    private List<UserPlan> userPlanList;

    @Builder
    public User(String name, LocalDate createdAt, LocalDate updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateUser(String name){
        this.name=name;
    }
}
