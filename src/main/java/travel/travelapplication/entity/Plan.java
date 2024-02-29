package travel.travelapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("Plan")
@Getter
public class Plan { // 커뮤니티 public 처리된 UserPlan

    @Id
    private ObjectId id;
    private String name;
    private String update; // 어떤 필드인지

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @DBRef
    private UserPlan userPlan;

    public Plan(String name, String update, UserPlan userPlan) {
        this.name = name;
        this.update = update;
        this.userPlan = userPlan;
    }
}
