package travel.travelapplication.plan.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.user.domain.UserPlan;

import java.util.Date;
import java.util.Optional;

@Document(collection = "Plan")
@Getter
@Setter
public class Plan { // 커뮤니티 public 처리된 UserPlan

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;
    private String name;

    private String userEmail;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @DBRef
    private UserPlan userPlan;

    @PersistenceCreator
    public Plan() {

    }

    @PersistenceCreator
    @Builder
    public Plan(String name, UserPlan userPlan, String userEmail) {
        this.name = name;
        this.userPlan = userPlan;
        this.userEmail=userEmail;
    }

    public void update(Plan updatedPlan) {
        Optional.ofNullable(updatedPlan.getName()).ifPresent(none -> this.name= updatedPlan.getName());
        Optional.ofNullable(updatedPlan.getUserPlan()).ifPresent(none -> this.userPlan= updatedPlan.getUserPlan());
        Optional.ofNullable(updatedPlan.getUserEmail()).ifPresent(none -> this.userEmail= updatedPlan.getUserEmail());
    }
}
