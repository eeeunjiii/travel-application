package travel.travelapplication.user;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import travel.travelapplication.place.domain.LikedPlace;
import travel.travelapplication.place.domain.Tag;
import travel.travelapplication.plan.domain.SavedPlan;

@Document("User")
@Getter
public class User {

    @Id
    @Setter // MemoryUserRepository 테스트용
    private ObjectId id;

    private String name;

    private String email;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @DBRef
    private List<UserPlan> userPlans = new ArrayList<>();

    @DBRef
    private List<Tag> tags = new ArrayList<>();

    @DBRef
    private List<LikedPlace> likedPlaces = new ArrayList<>();

    @DBRef
    private List<SavedPlan> savedPlans = new ArrayList<>();

    public User(String name, String email, List<UserPlan> userPlans, List<Tag> tags,
                List<LikedPlace> likedPlaces, List<SavedPlan> savedPlans) {
        this.name = name;
        this.email = email;
        this.userPlans = userPlans;
        this.tags = tags;
        this.likedPlaces = likedPlaces;
        this.savedPlans = savedPlans;
    }
}
