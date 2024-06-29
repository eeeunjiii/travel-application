package travel.travelapplication.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import travel.travelapplication.constant.Role;
import travel.travelapplication.place.domain.LikedPlace;
import travel.travelapplication.place.domain.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("User")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    private String name;

    private String email;

    private String role;

    @DBRef
    private List<UserPlan> userPlans = new ArrayList<>();

    @DBRef
    private List<Tag> tags = new ArrayList<>();

    @DBRef
    private List<LikedPlace> likedPlaces = new ArrayList<>();

    @DBRef
    private List<SavedPlan> savedPlans = new ArrayList<>();

    private String refreshToken;

    private String accessToken;

    @PersistenceCreator
    public User() {
    }

    @PersistenceCreator
    @Builder
    public User(String name, String email, List<UserPlan> userPlans, List<Tag> tags,
                List<LikedPlace> likedPlaces, List<SavedPlan> savedPlans, String role, String accessToken) {
        this.name = name;
        this.email = email;
        this.userPlans = userPlans;
        this.tags = tags;
        this.likedPlaces = likedPlaces;
        this.savedPlans = savedPlans;
        this.role = role;
        this.accessToken = accessToken;
    }

    public User updateUserName(String name) {
        this.name = name;
        return this;
    }

    public void addUserPlan(UserPlan userPlan) {
        this.userPlans.add(userPlan);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addLikedPlace(LikedPlace likedPlace) {
        this.likedPlaces.add(likedPlace);
    }

    public void addSavedPlan(SavedPlan savedPlan) {
        this.savedPlans.add(savedPlan);
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
