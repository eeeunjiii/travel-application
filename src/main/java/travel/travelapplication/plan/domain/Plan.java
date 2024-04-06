package travel.travelapplication.plan.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import travel.travelapplication.user.domain.UserPlan;

@Document("Plan")
@Getter
@AllArgsConstructor
public class Plan { // 커뮤니티 public 처리된 UserPlan

    @Id
    private ObjectId id;
    private String name;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @DBRef
    private UserPlan userPlan;

}
